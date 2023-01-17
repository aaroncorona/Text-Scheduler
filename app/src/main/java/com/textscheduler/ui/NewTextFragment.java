package com.textscheduler.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.textscheduler.R;
import com.textscheduler.databinding.FragmentNewTextBinding;
import com.textscheduler.sms.Sms;
import com.textscheduler.sms.SmsReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewTextFragment extends Fragment {

    private FragmentNewTextBinding binding;
    private static Sms sms;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentNewTextBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NewTextFragment.this)
                        .navigate(R.id.action_NewTextFragment_to_HomeFragment);
            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get SMS and time parameters from the text input boxes
                String date = String.valueOf(binding.textDateInput.getText());
                String time = String.valueOf(binding.textTimeInput.getText());
                String phone = String.valueOf(binding.textPhoneInput.getText());
                String message = String.valueOf(binding.textMessageInput.getText());
                // SMS obj
                sms = new Sms(phone, message);
                // Intent
                Context context = getContext();
                Intent intent = new Intent(context, SmsReceiver.class);
                intent.setAction("com.textscheduler.text");
                PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                // Schedule settings
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                try {
                    calendar.setTime(df.parse(date));
                } catch (ParseException e) {
                    calendar.setTimeInMillis(System.currentTimeMillis());
                }
                try {
                    calendar.set(Calendar.HOUR, Integer.parseInt(time.substring(0,2)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3)));
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {}
                // Alarm
                AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                Toast.makeText(context, "Text scheduled", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static Sms getSms() {
        return sms;
    };

}