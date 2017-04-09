package com.example.katecatlin.diversityapp.messages;

import android.content.Context;

import it.slyce.messaging.message.GeneralOptionsMessage;
import it.slyce.messaging.message.messageItem.MessageItem;

public class VerticalGeneralOptionsMessage extends GeneralOptionsMessage {

    @Override
    public MessageItem toMessageItem(final Context context) {
        return new CustomMessageGeneralOptionsItem(this, context);
    }

}
