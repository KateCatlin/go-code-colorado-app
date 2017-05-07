package com.example.katecatlin.diversityapp.activities;

import android.text.TextUtils;

import com.example.katecatlin.diversityapp.interfaces.ChatLogicInterface;
import com.example.katecatlin.diversityapp.models.Followup;
import com.example.katecatlin.diversityapp.models.Question;
import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.slyce.messaging.message.Message;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

/**
 * Created by katecatlin on 4/25/17.
 */

public class ChatLogic {
    ChatLogicInterface chatLogicInterface;
    private List<Question> questions;
    private Question currentQuestion;
    private List<String> serverRelevantResponses = new ArrayList<>();


    public ChatLogic(InputStream inputStream, ChatLogicInterface chatLogicInterface) {
        questions = readQuestionsFromJson(inputStream);
        this.chatLogicInterface = chatLogicInterface;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    private List<Question> readQuestionsFromJson(InputStream inputStream) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            return new Gson().fromJson(reader, QuestionFlow.class).getData();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    public void maybeInsertFollowupQuestions(String textAnswer) {
        boolean isFollowUp = currentQuestion.getFollowup() != null && !currentQuestion.getFollowup().isEmpty();

        if (isFollowUp) {
            for (Followup followup: currentQuestion.getFollowup()) {
                if (followup.getMatchedResponse().equalsIgnoreCase(textAnswer)) {
                    List<Question> updatedQuestions = new ArrayList<>();
                    updatedQuestions.addAll(followup.getFollowupQuestions());
                    updatedQuestions.addAll(questions);
                    questions = updatedQuestions;
                }
            }
        }
    }

    public boolean areThereMoreQuestions() {
        return !questions.isEmpty();
    }


    public void updateCurrentQuestion() {
        currentQuestion = questions.get(0);
        questions.remove(0);

        if (currentQuestion.getResponse() == null) {
            return;
        }

        String questionType = currentQuestion.getResponse().getType();

    }

    public void askCurrentQuestion() {
        if (currentQuestion.getResponse() == null) {
            final TextMessage currentMessage = new TextMessage();
            currentMessage.setText(currentQuestion.getPrompt());
            configureMessage(currentMessage, true);
            messagingFragment.addNewMessage(currentMessage);

            handleQuestionAnswered();
            return;
        }

        String questionType = currentQuestion.getResponse().getType();
        switch (questionType) {
            case "user-entry":
                final TextMessage currentMessage = new TextMessage();
                currentMessage.setText(currentQuestion.getPrompt());
                configureMessage(currentMessage, true);
                messagingFragment.addNewMessage(currentMessage);
                break;
            case "binary":
                presentChoiceMessage(currentQuestion.getPrompt(), Arrays.asList("Yes", "No"));
                break;
            case "choice":
                presentChoiceMessage(currentQuestion.getPrompt(), currentQuestion.getResponse().getChoices());
                break;
        }
    }


    public void configureMessage(Message message, boolean fromBot) {
        message.setSource(fromBot ? MessageSource.EXTERNAL_USER : MessageSource.LOCAL_USER);
        message.setDate(new Date().getTime());

        if (fromBot) {
            message.setAvatarUrl("file:///android_asset/ic_avatar.png");
        }
    }

    public void maybeStoreQuestionResponse(String text) {
        final String serverKey = currentQuestion.getServerKey();
        final String experiment = currentQuestion.getResponse().getChoices().get(optionSelected);

        if (serverKey != null) {
            serverRelevantResponses.add(text);
        }
    }

    public String getServerPath() {
        return TextUtils.join("/", serverRelevantResponses).replaceAll("\\s+", "").toLowerCase();
    }
}
