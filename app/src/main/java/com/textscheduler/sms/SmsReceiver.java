package com.textscheduler.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.textscheduler.ui.NewTextFragment;

// Class to handle broadcasts for Sms messages, in which a text is sent as a result
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Send the text
        Sms sms = NewTextFragment.getSms();
        sms.sendSms();
        Toast.makeText(context, sms.getSendResult(), Toast.LENGTH_SHORT).show();
    }
}

