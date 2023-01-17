package com.textscheduler.sms;

import android.icu.util.Calendar;
import android.telephony.SmsManager;

// Class where each object configures an SmsMessage
public class Sms {
    String phone, message;
    String sendDatetime;
    String sendResult;

    public Sms(String phone, String message) {
        this.phone = phone;
        this.message = message;

        // Set default values
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        sendDatetime = String.valueOf(calendar.getTime());
        sendResult = "No Message Requested";
    }

    public Sms(String phone, String message, String sendDatetime) {
        this.phone = phone;
        this.message = message;
        this.sendDatetime = sendDatetime;

        // Set default values
        sendResult = "No Message Requested";
    }

    public void sendSms() {
        // Send text if valid number
        if(isValid()) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, message, null, null);
            sendResult = "Text Message sent to " + getPhone();
        } else {
            sendResult = "Invalid settings, Text Message not sent";
        }
    }

    public boolean isValid() {
        if(hasValidNumber() && hasValidMessage()) {
            return true;
        }
        return false;
    }

    // Helper to avoid app crashes from unreachable SMS destinations
    private boolean hasValidNumber() {
        if(phone.length() <= 2) {
            return false;
        }
        try{
            int num = Integer.parseInt(phone);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Helper to avoid blank messages that wont send
    private boolean hasValidMessage() {
        if(message.length() == 0) {
            return false;
        }
        return true;
    }

    public String getSendResult() {
        return sendResult;
    }

    public String getPhone() {
        return phone;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public String getMessage() {
        return message;
    }
}
