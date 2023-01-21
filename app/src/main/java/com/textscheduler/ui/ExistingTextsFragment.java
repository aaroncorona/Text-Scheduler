package com.textscheduler.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.textscheduler.R;
import com.textscheduler.databinding.FragmentExistingTextsBinding;
import com.textscheduler.sms.Sms;
import com.textscheduler.smsdatabase.SmsEntity;
import com.textscheduler.smsdatabase.SmsRepository;

import java.util.ArrayList;
import java.util.List;

public class ExistingTextsFragment extends Fragment {

    private FragmentExistingTextsBinding binding;
    private View view;
    private ListView listView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentExistingTextsBinding.inflate(inflater, container, false);
        view = inflater.inflate(R.layout.fragment_existing_texts, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get all SMS texts in the database
        List<SmsEntity> smsAll = MainActivity.getAllSmsRecords();
        // Convert to String and only preserve texts not sent
        List<String> smsAllString = new ArrayList<>();
        for(int i = 0; i < smsAll.size(); i++) {
            Sms sms = new Sms(smsAll.get(i).getRecipientNumber(), "", smsAll.get(i).getSendDatetime());
            if(!sms.wasSent()) {
                smsAllString.add(smsAll.get(i).toString());
            }
        }
        // Populate list view
        listView = (ListView) view.findViewById(R.id.sms_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.sms_list, R.id.textView, smsAllString); // set where to populate the list
        listView.setAdapter(adapter); // set where the populated list shows up

        // Button to return home
        binding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ExistingTextsFragment.this)
                        .navigate(R.id.action_ExistingTextsFragment_to_HomeFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}