package com.example.katecatlin.diversityapp.activities;

import com.example.katecatlin.diversityapp.models.Question;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by katecatlin on 4/25/17.
 */
public class ChatLogicTest {
    static InputStream inputStream;
    static List<Question> questions;
    static ChatLogic chatLogic;

    @BeforeClass
    public static void getInputStream() {
        inputStream = ChatLogicTest.class.getResourceAsStream("/questions.json");
        chatLogic = new ChatLogic(inputStream);
        questions = chatLogic.getQuestions();
    }

    @Test
    public void parseNull() throws Exception {
        ChatLogic chatLogicNullTest = new ChatLogic(null);
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
    public void testNewQuestion() throws Exception {
        Question expectedQuestion = questions.get(1);
        Question actualQuestion = chatLogic.newQuestion();
        assertNotEquals(expectedQuestion, actualQuestion);

        actualQuestion = chatLogic.newQuestion();
        assertEquals(expectedQuestion, actualQuestion);
    }

}