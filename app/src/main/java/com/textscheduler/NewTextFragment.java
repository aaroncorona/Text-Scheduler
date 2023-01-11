package com.textscheduler;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.textscheduler.databinding.FragmentNewTextBinding;

public class NewTextFragment extends Fragment {

    private FragmentNewTextBinding binding;

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
                // Get text parameters from the text boxes
                String phone = String.valueOf(binding.textPhoneInput.getText());
                String date = String.valueOf(binding.textDateInput.getText());
                String time = String.valueOf(binding.textTimeInput.getText());
                String message = String.valueOf(binding.textMessageInput.getText());
                // Text message settings
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message, null, null);
                // TODO Schedule settings
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}