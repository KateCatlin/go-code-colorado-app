package com.example.katecatlin.diversityapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.katecatlin.diversityapp.R;
import com.example.katecatlin.diversityapp.interfaces.ChatLogicInterface;
import com.example.katecatlin.diversityapp.messages.VerticalGeneralOptionsMessage;
import com.example.katecatlin.diversityapp.models.Question;

import java.io.IOException;
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

public class ChatActivity extends AppCompatActivity implements UserSendsMessageListener, OnOptionSelectedListener, ChatLogicInterface {

//    public static final List<String> BINARY_QUESTION_CHOICES = Arrays.asList("yes", "no");
    public static final String FULL_URL_KEY = "FULL_URL_KEY";

    private SlyceMessagingFragment messagingFragment;

    private ChatLogic chatLogic;
    public static String TAG = "TAG";
    public List<String> questionResponseChoices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagingFragment = (SlyceMessagingFragment) getFragmentManager().findFragmentById(R.id.messaging_fragment);
        messagingFragment.setOnSendMessageListener(this);
        messagingFragment.setStyle(R.style.chat_styles);

        try {
            chatLogic = new ChatLogic(getAssets().open("question_flow.json"), this);
        } catch (IOException e) {
        }


        chatLogic.updateCurrentQuestion();
    }

    private void askCurrentQuestion(TextMessage textMessage, List<String> options) {

        if (options == null) {
            messagingFragment.addNewMessage((textMessage));
        } else {
            dismissKeyboard(findViewById(android.R.id.content));
            messagingFragment.addNewMessage(textMessage);

            final GeneralOptionsMessage currentMessage = new VerticalGeneralOptionsMessage();
            currentMessage.setOnOptionSelectedListener(this);
            currentMessage.setOptions(options.toArray(new String[options.size()]));

            messagingFragment.addNewMessage(currentMessage);
        }
    }

    private void handleQuestionAnswered() {
        if (chatLogic.areThereMoreQuestions()) {
            chatLogic.updateCurrentQuestion();
        } else {
            advanceToStats();
        }
    }

//    switch (questionType) {
//            case "user-entry":
//                messagingFragment.addNewMessage(textMessage);
//                break;
//            case "binary":
//                presentChoiceMessage(currentQuestion.getPrompt(), Arrays.asList("Yes", "No"));
//                break;
//            case "choice":
//                presentChoiceMessage(currentQuestion.getPrompt(), currentQuestion.getResponse().getChoices());
//                break;
//        }
//    private void presentChoiceMessage(String title, List<String> options) {
//        final TextMessage questionMessage = new TextMessage();
//        questionMessage.setText(title);
//        questionMessage.setText(currentQuestion.getPrompt());
//        configureMessage(questionMessage, true);
//        messagingFragment.addNewMessage(questionMessage);
//
//        final GeneralOptionsMessage currentMessage = new VerticalGeneralOptionsMessage();
//        configureMessage(currentMessage, true);
//        currentMessage.setOnOptionSelectedListener(this);
//        currentMessage.setOptions(options.toArray(new String[options.size()]));
//
//        messagingFragment.addNewMessage(currentMessage);
//    }

    //    @Override
//    public void maybeDismissKeyboard () {
//        if (questionType.equals("binary") || questionType.equals("choice")) {
//            ChatActivity.dismissKeyboard(findViewById(android.R.id.content));
//        }
//    }

    @Override
    public void onUserSendsTextMessage(final String text) {
        chatLogic.maybeStoreQuestionResponse(text);
        chatLogic.maybeInsertFollowupQuestions(text);
        handleQuestionAnswered();
    }


    @Override
    public void onUserSendsMediaMessage(final Uri imageUri) {}

    private void advanceToStats() {
        String baseURL = "https://salty-refuge-57490.herokuapp.com/";
        String fullURL = baseURL + chatLogic.getServerPath();

        Intent intent = new Intent(this, StatActivity.class);
        intent.putExtra(FULL_URL_KEY, fullURL);
        startActivity(intent);
    }

    @Override
    public String onOptionSelected(final int optionSelected) {
        String response;

        response = questionResponseChoices.get(optionSelected);

        final TextMessage questionMessage = new TextMessage();
        questionMessage.setText(response);
        chatLogic.configureMessage(questionMessage, false);
        messagingFragment.addNewMessage(questionMessage);

        chatLogic.maybeStoreQuestionResponse(response);
        chatLogic.maybeInsertFollowupQuestions(response);
        handleQuestionAnswered();

        return ""; // not used!
    }


    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    @Override
    public void callback(TextMessage currentMessage, List<String> options) {
        questionResponseChoices = options;
        askCurrentQuestion(currentMessage, questionResponseChoices);

    }
}
