package com.example.katecatlin.diversityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by katecatlin on 4/8/17.
 */

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    private void advanceToStats() {
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }

}
