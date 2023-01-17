package com.textscheduler.sms;

import android.telephony.SmsManager;

// Class where each object configures an SmsMessage
public class Sms {
    String phone, message;
    String sendResult;

    public Sms(String phone, String message) {
        this.phone = phone;
        this.message = message;
        sendResult = "No Message Requested"; // default value
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

    public String getMessage() {
        return message;
    }
}
