package com.example.katecatlin.diversityapp.activities;

import android.content.Context;

import com.example.katecatlin.diversityapp.interfaces.ChatLogicInterface;
import com.example.katecatlin.diversityapp.models.Question;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.slyce.messaging.message.Message;
import it.slyce.messaging.message.MessageSource;
import it.slyce.messaging.message.messageItem.MessageItem;

import static it.slyce.messaging.message.MessageSource.EXTERNAL_USER;
import static org.junit.Assert.*;

/**
 * Created by katecatlin on 4/25/17.
 */
public class ChatLogicTest {
    static InputStream inputStream;
    static List<Question> questions;
    static ChatLogic chatLogic;
    static ChatLogicInterface chatLogicInterface;

    @BeforeClass
    public static void getInputStream() {
        inputStream = ChatLogicTest.class.getResourceAsStream("/questions.json");
        chatLogic = new ChatLogic(inputStream, chatLogicInterface);
        questions = chatLogic.getQuestions();
    }

    @Test
    public void parseNull() throws Exception {
        ChatLogic chatLogicNullTest = new ChatLogic(null, null);
        List<Question> questions = chatLogicNullTest.getQuestions();
        assertNotNull(questions);
        assertEquals(0, questions.size());
    }

    @Test
    public void parseJson() throws Exception {
        assertNotNull(questions);
        assertEquals(12, questions.size());
        assertEquals("questions not retrieved properly", questions.get(0).getPrompt(), "What's the title of the job?" );
    }

    @Test
    public void updateCurrentQuestion() throws Exception {
        Question originalQuestion = questions.get(0);
        Question expectedUpdatedQuestion = questions.get(1);
        chatLogic.updateCurrentQuestion();
        Question actualUpdatedQuestion = questions.get(0);
        assertNotEquals(originalQuestion, actualUpdatedQuestion);
        assertEquals(expectedUpdatedQuestion, actualUpdatedQuestion);
    }

    @Test
    public void configureMessage() throws Exception {
        Boolean fromBot;
        Message message = new Message() {
            @Override
            public MessageItem toMessageItem(Context context) {
                return null;
            }
        };
        chatLogic.configureMessage(message, true);
        assertEquals(message.getSource(), EXTERNAL_USER);
        assertNotNull(message.getAvatarUrl());
        assertNotNull(message.getDate());
    }


}