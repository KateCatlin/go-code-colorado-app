package com.example.katecatlin.diversityapp.interfaces;

import android.app.Activity;

import java.util.List;

import it.slyce.messaging.message.TextMessage;

/**
 * Created by katecatlin on 5/7/17.
 */

public interface ChatLogicInterface {
    public void callback(TextMessage textMessage, List<String> options);
}
