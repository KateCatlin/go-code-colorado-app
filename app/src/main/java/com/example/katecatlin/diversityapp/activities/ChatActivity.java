package com.example.katecatlin.diversityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.katecatlin.diversityapp.R;
import com.example.katecatlin.diversityapp.interfaces.ChatLogicInterface;
import com.example.katecatlin.diversityapp.messages.VerticalGeneralOptionsMessage;

import java.io.IOException;
import java.util.List;

import it.slyce.messaging.SlyceMessagingFragment;
import it.slyce.messaging.listeners.OnOptionSelectedListener;
import it.slyce.messaging.listeners.UserSendsMessageListener;
import it.slyce.messaging.message.GeneralOptionsMessage;
import it.slyce.messaging.message.TextMessage;

/**
 * Created by katecatlin on 4/8/17.
 */

public class ChatActivity extends AppCompatActivity implements UserSendsMessageListener, OnOptionSelectedListener, ChatLogicInterface {

    public static final String FULL_URL_KEY = "FULL_URL_KEY";
    private SlyceMessagingFragment messagingFragment;
    private ChatLogic chatLogic;
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
            //If there's no options, it means it's a user-entry question
            messagingFragment.addNewMessage(textMessage);
        } else {
            //This means it's a multiple-choice or binary question
            dismissKeyboard(findViewById(android.R.id.content));
            messagingFragment.addNewMessage(textMessage);

            final GeneralOptionsMessage currentMessage = new VerticalGeneralOptionsMessage();
            currentMessage.setOnOptionSelectedListener(this);
            currentMessage.setOptions(options.toArray(new String[options.size()]));

            messagingFragment.addNewMessage(currentMessage);
        }
    }


    @Override
    public void onUserSendsMediaMessage(final Uri imageUri) {}

    @Override
    public void onUserSendsTextMessage(final String text) {
        chatLogic.handleQuestionAnswered(text);
    }

    @Override
    public String onOptionSelected(final int optionSelected) {
        String response;

        response = questionResponseChoices.get(optionSelected);

        final TextMessage questionMessage = new TextMessage();
        questionMessage.setText(response);
        chatLogic.configureMessage(questionMessage, false);
        messagingFragment.addNewMessage(questionMessage);

        chatLogic.handleQuestionAnswered(response);

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

    @Override
    public void advanceToStats(String URLToCall) {
        //This is called through the interface when there are no more questions to ask.
        Intent intent = new Intent(this, StatActivity.class);
        intent.putExtra(FULL_URL_KEY, URLToCall);
        startActivity(intent);
    }
}
