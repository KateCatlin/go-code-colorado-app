package com.example.katecatlin;

import android.util.Log;

import com.example.katecatlin.diversityapp.activities.ChatActivity;
import com.example.katecatlin.diversityapp.models.Question;
import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by katecatlin on 4/23/17.
 * A test suite containing all tests for my application.
 */

public final class ChatActivityTest {
    private ChatActivity chatActivity;
    private List<Question> questions;
    private static final String TAG = "ChatActivityTest";


    @Before
    public void setUp() {
        chatActivity = new ChatActivity();
        Log.d(TAG, "launched");

        try {
            final InputStream inputStream = chatActivity.getAssets().open("question_flow.json");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            questions = new Gson().fromJson(reader, QuestionFlow.class).getData();
            Log.d(TAG, questions.get(0).toString());
        } catch (Exception e) {
        }
    }

    @Test
    public void testSquareOfSum5() {
        final int expected = 25;
        final int actual = 5*5;
        assertEquals(expected, actual);
    }

    @Test
    public void firstQuestionOccurs() {
        final String expected = new String("What's the title of the job?");
        final String actual = questions.get(0).toString();
        assertEquals(expected, actual);
    }
}