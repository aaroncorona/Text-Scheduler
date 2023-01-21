package com.textscheduler.sms;

import android.icu.util.Calendar;
import android.telephony.SmsManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;

// Class where each object configures an SmsMessage
public final class Sms {
    String recipientNumber, messageBody;
    String sendDatetime;
    String sendResult;

    public Sms(String recipientNumber, String messageBody) {
        this.recipientNumber = recipientNumber;
        this.messageBody = messageBody;

        // Set default values
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        sendDatetime = String.valueOf(calendar.getTime());
        sendResult = "No Message Requested";
    }

    public Sms(String recipientNumber, String messageBody, String sendDatetime) {
        this.recipientNumber = recipientNumber;
        this.messageBody = messageBody;
        this.sendDatetime = sendDatetime;

        // Set default values
        sendResult = "No Message Requested";
    }

    public void sendSms() {
        // Send text if valid number
        if(isValid()) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(recipientNumber, null, messageBody, null, null);
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
        if(recipientNumber.length() <= 2) {
            return false;
        }
        try{
            int num = Integer.parseInt(recipientNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // Helper to avoid blank messages that wont send
    private boolean hasValidMessage() {
        if(messageBody.length() == 0) {
            return false;
        }
        return true;
    }

    // Check if the SMS send datetime already passed
    public boolean wasSent() {
        // Extract the send date and compare it to the current time
        Calendar calSendDatetime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            calSendDatetime.setTime(df.parse(sendDatetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calCurrentTime = Calendar.getInstance();
        calCurrentTime.setTimeInMillis(System.currentTimeMillis());
        if(calCurrentTime.compareTo(calSendDatetime) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getSendResult() {
        return sendResult;
    }

    public String getPhone() {
        return recipientNumber;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public String getMessage() {
        return messageBody;
    }

    @Override
    public String toString() {
        return "Send to " + recipientNumber + "; \nSend at " + sendDatetime.substring(4);
    }
}
