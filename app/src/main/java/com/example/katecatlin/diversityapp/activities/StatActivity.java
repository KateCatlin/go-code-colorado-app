package com.example.katecatlin.diversityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.example.katecatlin.diversityapp.R;

/**
 * Created by katecatlin on 4/8/17.
 */

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String url = intent.getStringExtra("FULL_URL_KEY");
        Log.d("TAG", url);


        setContentView(R.layout.activity_stat);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(url);
    }
}