package com.example.katecatlin.diversityapp.messages;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

import it.slyce.messaging.message.GeneralOptionsMessage;
import it.slyce.messaging.message.messageItem.MessageViewHolder;
import it.slyce.messaging.message.messageItem.general.generalOptions.MessageGeneralOptionsItem;
import it.slyce.messaging.message.messageItem.general.generalOptions.MessageGeneralOptionsViewHolder;

class CustomMessageGeneralOptionsItem extends MessageGeneralOptionsItem {

    CustomMessageGeneralOptionsItem(final GeneralOptionsMessage generalOptionsMessage, final Context context) {
        super(generalOptionsMessage, context);
    }

    @Override
    public void buildMessageItem(final MessageViewHolder messageViewHolder) {
        super.buildMessageItem(messageViewHolder);

        final MessageGeneralOptionsViewHolder viewHolder = (MessageGeneralOptionsViewHolder) messageViewHolder;
        viewHolder.optionsLinearLayout.setOrientation(LinearLayout.VERTICAL);
        viewHolder.optionsLinearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
    }

}
