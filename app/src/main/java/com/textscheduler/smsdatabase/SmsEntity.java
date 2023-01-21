package com.textscheduler.smsdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Each instance of an SmsEntity represents a row in a Sms table in the database. Each row
// provides info on a SMS that has been scheduled to be sent
@Entity(tableName = "sms")
public final class SmsEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid;

    @ColumnInfo(name = "recipient_number")
    private String recipientNumber;

    @ColumnInfo(name = "send_datetime")
    private String sendDatetime;

    // Getters & Setters
    public int getId() {
        return uid;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    protected void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    protected void setSendDatetime(String sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

    @Override
    public String toString() {
        return "ID " + uid + "; Text " + recipientNumber + "; Send at " + sendDatetime;
    }
}

