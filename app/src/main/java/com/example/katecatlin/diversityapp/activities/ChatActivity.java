package com.example.katecatlin.diversityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.katecatlin.diversityapp.R;
import com.example.katecatlin.diversityapp.messages.VerticalGeneralOptionsMessage;
import com.example.katecatlin.diversityapp.models.Question;
import com.example.katecatlin.diversityapp.models.Followup;
import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.OnOptionSelectedListener;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.GeneralOptionsMessage;
import it.slyce.messaging.message.Message;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.TextMessage;

/**
 * Created by katecatlin on 4/8/17.
 */

public class ChatActivity extends AppCompatActivity implements UserSendsMessageListener, OnOptionSelectedListener {

    public static final List<String> BINARY_QUESTION_CHOICES = Arrays.asList("yes", "no");

    private Question currentQuestion;

    private SlyceMessagingFragment messagingFragment;

    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagingFragment = (SlyceMessagingFragment) getFragmentManager().findFragmentById(R.id.messaging_fragment);
        messagingFragment.setOnSendMessageListener(this);

        try {
            final InputStream inputStream = getAssets().open("question_flow.json");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            questions = new Gson().fromJson(reader, QuestionFlow.class).getData();
        } catch (Exception e) {
        }

        updateCurrentQuestion();
        askCurrentQuestion();
    }

    private void askCurrentQuestion() {
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

    private void presentChoiceMessage(String title, List<String> options) {
        final TextMessage questionMessage = new TextMessage();
        questionMessage.setText(title);
        questionMessage.setText(currentQuestion.getPrompt());
        configureMessage(questionMessage, true);
        messagingFragment.addNewMessage(questionMessage);

        final GeneralOptionsMessage currentMessage = new VerticalGeneralOptionsMessage();
        configureMessage(currentMessage, true);
        currentMessage.setOnOptionSelectedListener(this);
        currentMessage.setOptions(options.toArray(new String[options.size()]));

        messagingFragment.addNewMessage(currentMessage);
    }

    private void updateCurrentQuestion() {


        currentQuestion = questions.get(0);
        questions.remove(0);

        String questionType = currentQuestion.getResponse().getType();

        if (questionType.equals("binary") || questionType.equals("choice")) {
            dismissKeyboard(findViewById(android.R.id.content));
        }
    }

    private boolean areThereMoreQuestions() {
        return !questions.isEmpty();
    }

    @Override
    public void onUserSendsTextMessage(final String text) {
        handleFollowUp(text);
        handleQuestionAnswered();
    }

    private void handleQuestionAnswered() {

        if (areThereMoreQuestions()) {
            updateCurrentQuestion();
            askCurrentQuestion();
        } else {
            advanceToStats();
        }
    }


    @Override
    public void onUserSendsMediaMessage(final Uri imageUri) {}

    private void advanceToStats() {
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }

    @Override
    public String onOptionSelected(final int optionSelected) {
        String response;

        switch (currentQuestion.getResponse().getType()) {
            case "binary":
                response = BINARY_QUESTION_CHOICES.get(optionSelected);
                break;
            case "choice":
                response = currentQuestion.getResponse().getChoices().get(optionSelected);
                break;
            default:
                throw new IllegalStateException("Unhandled question type");
        }

        final TextMessage questionMessage = new TextMessage();
        questionMessage.setText(response);
        configureMessage(questionMessage, false);
        messagingFragment.addNewMessage(questionMessage);

        handleFollowUp(response);
        handleQuestionAnswered();

        return ""; // not used!
    }

    private void handleFollowUp (String textAnswer) {
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

    private void configureMessage(Message message, boolean fromBot) {
        message.setSource(fromBot ? MessageSource.EXTERNAL_USER : MessageSource.LOCAL_USER);
        message.setDate(new Date().getTime());

        if (fromBot) {
            message.setAvatarUrl("https://cdn.dribbble.com/users/28681/screenshots/2810499/robotheadshot01-dribbble_1x.jpg");
        }
    }

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
