package com.example.katecatlin.diversityapp.activities;

import com.example.katecatlin.diversityapp.models.Question;
import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

/**
 * Created by katecatlin on 4/25/17.
 */

public class ChatLogic {
    private final List<Question> questions;

    public ChatLogic(InputStream inputStream) {
        questions = readQuestionsFromJson(inputStream);
    }

    private List<Question> readQuestionsFromJson(InputStream inputStream) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            return new Gson().fromJson(reader, QuestionFlow.class).getData();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
