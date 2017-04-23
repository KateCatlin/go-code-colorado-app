package com.example.katecatlin;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.filters.LargeTest;
import android.test.ActivityInstrumentationTestCase2;

import com.example.katecatlin.diversityapp.activities.ChatActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by katecatlin on 4/23/17.
 */

@RunWith(JUnit4.class)
@LargeTest

public class AndroidTest extends ChatActivity {

    public AndroidTest() {
        super(ChatActivity.class);
    }
    @Test
    public void evaluatesExpression() {
        updateCurrentQuestion calculator = new updateCurrentQuestion();
        int sum = calculator.evaluate("1+2+3");
        assertEquals(6, sum);
    }
}