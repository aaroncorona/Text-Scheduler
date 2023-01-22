package com.textscheduler;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.textscheduler.smsdatabase.SmsDao;
import com.textscheduler.smsdatabase.SmsDatabase;
import com.textscheduler.smsdatabase.SmsEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SmsDatabaseTest {
    private SmsDao dao;
    private SmsDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SmsDatabase.class).build();
        dao = db.getSmsDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertedSms_existsInDb() throws Exception {
        SmsEntity smsRow = new SmsEntity();
        smsRow.setRecipientNumber("311");
        List<SmsEntity> allRecordsForNumber = dao.getAllByNumber(smsRow.getRecipientNumber());
        assertEquals(allRecordsForNumber.size(), 0);
        dao.insertAll(smsRow);
        allRecordsForNumber = dao.getAllByNumber(smsRow.getRecipientNumber());
        assertEquals(allRecordsForNumber.size(), 1);
    }
}
