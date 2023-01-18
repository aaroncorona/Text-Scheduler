package com.textscheduler.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

// Class to handle broadcasts for Sms messages, in which a text is sent as a result
public final class SmsReceiver extends BroadcastReceiver {

    // Send the text using the intent
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String recipientNumber = "";
        String messageBody = "";
        if (bundle != null) {
            recipientNumber = bundle.getString("recipient_number");
            messageBody = bundle.getString("message_body");
        }
        // Send the text
        Sms sms = new Sms(recipientNumber, messageBody);
        sms.sendSms();
        Toast.makeText(context, sms.getSendResult(), Toast.LENGTH_SHORT).show();
    }
}

