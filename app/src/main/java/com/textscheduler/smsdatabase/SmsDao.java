package com.textscheduler.smsdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SmsDao {
    @Query("SELECT * FROM sms")
    List<SmsEntity> getAll();

    @Query("SELECT * FROM sms WHERE uid IN (:userIds)")
    List<SmsEntity> getAllByID(int[] userIds);

    @Query("SELECT * FROM sms WHERE recipient_number LIKE :recipientNumber")
    List<SmsEntity> getAllByNumber(String recipientNumber);

    @Query("SELECT * FROM sms WHERE recipient_number LIKE :recipientNumber AND " +
            "send_datetime LIKE :sendDatetime")
    List<SmsEntity> getAllByNumberAndTime(String sendDatetime, String recipientNumber);

    @Insert
    void insertAll(SmsEntity... sms);

    @Delete
    void delete(SmsEntity sms);

    @Query("DELETE FROM sms")
    public void nukeTable();
}