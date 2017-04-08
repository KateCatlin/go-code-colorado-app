package com.example.katecatlin.diversityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.katecatlin.diversityapp.models.QuestionFlow;
import com.example.katecatlin.diversityapp.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by katecatlin on 4/8/17.
 */

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        QuestionFlow questionFlow;

        try {
            final InputStream inputStream = getAssets().open("question_flow.json");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            questionFlow = new Gson().fromJson(reader, QuestionFlow.class);
        } catch (Exception e) {
        }
    }

    private void advanceToStats() {
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }

}
