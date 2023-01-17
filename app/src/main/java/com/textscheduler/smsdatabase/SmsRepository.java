package com.textscheduler.smsdatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.textscheduler.sms.Sms;

import java.util.List;

public final class SmsRepository {

    private static final SmsRepository instance = new SmsRepository();
    private SmsDatabase db;
    private SmsDao dao;

    // Singleton design
    private SmsRepository() {
    }

    public static SmsRepository getInstance(Context context) {
        instance.createDB(context);
        return instance;
    }

    private void createDB(Context context) {
        db = Room.databaseBuilder(context, SmsDatabase.class, "sms-database").build();
        dao = db.smsDao();
    }

    public void insertRecord(Sms sms) {
        if(sms.isValid()) {
            // Create entity obj (i.e. define the row)
            SmsEntity smsRow = new SmsEntity();
            smsRow.setRecipientNumber(sms.getPhone());
            smsRow.setSendDatetime(sms.getSendDatetime());
            // Insert row
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    dao.insertAll(smsRow);
                    return null;
                }
            }.execute();
        }
    }

    public List<SmsEntity>  getAllRecords() {
        final List<SmsEntity>[] smsAll = new List[]{null};
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                smsAll[0] = dao.getAll();
                return null;
            }
        }.execute();
        return smsAll[0];
    }
}
