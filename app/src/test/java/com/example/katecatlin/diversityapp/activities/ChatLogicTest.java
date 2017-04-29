package com.example.katecatlin.diversityapp.activities;

import com.example.katecatlin.diversityapp.models.Question;

import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by katecatlin on 4/25/17.
 */
public class ChatLogicTest {
    @Test
    public void parseNull() throws Exception {
        ChatLogic chatLogic = new ChatLogic(null);
        List<Question> questions = chatLogic.getQuestions();
        assertNotNull(questions);
        assertEquals(0, questions.size());
    }

    @Test
    public void parseJson() throws Exception {
        InputStream inputStream = ChatLogicTest.class.getResourceAsStream("/questions.json");
        ChatLogic chatLogic = new ChatLogic(inputStream);
        List<Question> questions = chatLogic.getQuestions();
        assertNotNull(questions);
        assertEquals(12, questions.size());
        assertEquals("questions not retrieved properly", questions.get(0).getPrompt(), "What's the title of the job?" );
    }

//    @Test

}