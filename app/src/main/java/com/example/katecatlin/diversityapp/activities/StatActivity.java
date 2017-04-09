package com.example.katecatlin.diversityapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.katecatlin.diversityapp.R;

/**
 * Created by katecatlin on 4/8/17.
 */

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("http://slashdot.org/");
    }
}