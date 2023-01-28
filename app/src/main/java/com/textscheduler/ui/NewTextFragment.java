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
import android.view.inputmethod.InputMethodManager;
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

    private static FragmentNewTextBinding binding;

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

        binding.buttonPickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactListDialogFragment dialogFragment = new ContactListDialogFragment();
                dialogFragment.show(getChildFragmentManager(), "fragment_dialog_contact_list");
            }
        });

        binding.buttonNewNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textPhoneInput.requestFocus();
                InputMethodManager lManager = (InputMethodManager) getActivity()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                lManager.showSoftInput(binding.textPhoneInput, 0);
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get SMS and time parameters from the text input boxes
                String phoneInput = String.valueOf(binding.textPhoneInput.getText());
                String dateInput = String.valueOf(binding.textDateInput.getText());
                String timeInput = String.valueOf(binding.textTimeInput.getText());
                String messageInput = String.valueOf(binding.textMessageInput.getText());
                // Schedule settings
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                try {
                    calendar.setTime(df.parse(dateInput));
                } catch (ParseException e) {
                    calendar.setTimeInMillis(System.currentTimeMillis());
                }
                try {
                    calendar.set(Calendar.HOUR, Integer.parseInt(timeInput.substring(0,2)));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeInput.substring(3)));
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {}
                // Validity check before scheduling the SMS to avoid app crashes
                Sms sms = new Sms(phoneInput, messageInput, String.valueOf(calendar.getTime()));
                if(sms.isValid()) {
                    // SMS Intent
                    Intent intent = new Intent(getContext(), SmsReceiver.class);
                    intent.putExtra("recipient_number", phoneInput);
                    intent.putExtra("message_body", messageInput);
                    intent.putExtra("send_datetime", String.valueOf(calendar.getTimeInMillis()));
                    intent.setAction("com.textscheduler.text");
                    PendingIntent pIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    // Alarm
                    AlarmManager alarm = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                    alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                    // Database record
                    MainActivity.insertSmsRecord(sms);
                    Toast.makeText(getContext(), "Text scheduled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Text not scheduled - Invalid input", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.buttonExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(NewTextFragment.this)
                        .navigate(R.id.action_NewTextFragment_to_ExistingTextsFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected static void setPhoneInput(String phone) {
        binding.textPhoneInput.setText(phone);
    }
}