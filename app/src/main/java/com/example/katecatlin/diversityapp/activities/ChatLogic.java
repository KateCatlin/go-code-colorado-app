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
import it.slyce.messaging.message.TextMessage;

import static it.slyce.messaging.message.MessageSource.EXTERNAL_USER;
import static it.slyce.messaging.message.MessageSource.LOCAL_USER;

/**
 * Created by katecatlin on 4/25/17.
 */

public class ChatLogic {
    ChatLogicInterface chatLogicInterface;
    private List<Question> questions;
    private Question currentQuestion;
    private List<String> serverRelevantResponses = new ArrayList<>();
    public List<String> questionResponseChoices;


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

    public boolean areThereMoreQuestions() {
        return !questions.isEmpty();
    }

    public void prepareNewTextMessage() {
        final TextMessage currentMessage = new TextMessage();
        configureMessage(currentMessage, true);

        updateCurrentQuestion();
        currentMessage.setText(currentQuestion.getPrompt());

        if (currentQuestion.getResponse() == null) {
            chatLogicInterface.callback(currentMessage, null);
        }
        else {
            String questionType = currentQuestion.getResponse().getType();
            switch (questionType) {
                case "user-entry":
                    questionResponseChoices = null;
                    chatLogicInterface.callback(currentMessage, questionResponseChoices);
                    break;
                case "binary":
                    questionResponseChoices = Arrays.asList("Yes", "No");
                    chatLogicInterface.callback(currentMessage, questionResponseChoices);
                    break;
                case "choice":
                    questionResponseChoices = currentQuestion.getResponse().getChoices();
                    chatLogicInterface.callback(currentMessage, questionResponseChoices);
                    break;
            }
        }
    }

    public void updateCurrentQuestion() {
        currentQuestion = questions.get(0);
        questions.remove(0);
    }

    public void configureMessage(Message message, boolean fromBot) {
        message.setDate(new Date().getTime());

        if (fromBot) {
            message.setSource(EXTERNAL_USER);
            message.setAvatarUrl("file:///android_asset/ic_avatar.png");
        } else {
            message.setSource(LOCAL_USER);
        }
    }

    public void handleQuestionAnswered(final String text) {
        maybeStoreQuestionResponse(text);
        maybeInsertFollowupQuestions(text);
        if (areThereMoreQuestions()) {
            updateCurrentQuestion();
        } else {
            assembleURLFromAnswers();
        }
    }

    public void maybeStoreQuestionResponse(String text) {
        final String serverKey = currentQuestion.getServerKey();

        if (serverKey != null) {
            serverRelevantResponses.add(text);
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

    public void assembleURLFromAnswers() {
        String baseURL = "https://salty-refuge-57490.herokuapp.com/";
        String fullURL = baseURL + getServerPath();
        chatLogicInterface.advanceToStats(fullURL);
    }


    public String getServerPath() {
        return TextUtils.join("/", serverRelevantResponses).replaceAll("\\s+", "").toLowerCase();
    }
}
