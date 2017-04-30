package com.example.katecatlin.diversityapp.activities;

import com.example.katecatlin.diversityapp.models.Followup;
import com.example.katecatlin.diversityapp.models.Question;
import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by katecatlin on 4/25/17.
 */

public class ChatLogic {
    private List<Question> questions;
    private Question tempQuestion;

    public ChatLogic(InputStream inputStream) {
        questions = readQuestionsFromJson(inputStream);
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

    public Question newQuestion() {
        tempQuestion = questions.get(0);
        questions.remove(0);
        return tempQuestion;
    }

    public void maybeInsertFollowupQuestions(String textAnswer) {
        boolean isFollowUp = tempQuestion.getFollowup() != null && !tempQuestion.getFollowup().isEmpty();

        if (isFollowUp) {
            for (Followup followup: tempQuestion.getFollowup()) {
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
}
