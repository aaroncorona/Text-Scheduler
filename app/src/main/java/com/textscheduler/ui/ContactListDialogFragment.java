package com.textscheduler.ui;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.textscheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ContactListDialogFragment extends DialogFragment {

    // A UI Fragment must inflate its View
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_contact_picker, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // List view where the contacts will populate
        ListView listView = (ListView) view.findViewById(R.id.contact_list);

        // Adapter
        List<String> contacts = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                R.layout.contact_view_for_list, R.id.contact_text_view, contacts);
        listView.setAdapter(adapter);

        // The Search button triggers Contact retrieval that populates on the ListView
        Button buttonSearch = (Button) view.findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get user input
                EditText textNameInput = (EditText) view.findViewById(R.id.text_name_input);
                String nameInput = String.valueOf(textNameInput.getText());
                // Return all matching contacts in the List
                List<String> tempList = getContacts(nameInput);
                for(int i=0; i < tempList.size(); i++) {
                    contacts.add(tempList.get(i));
                }
                if(contacts.size() < 1) {
                    Toast.makeText(getContext(), "No Matching Contacts Found", Toast.LENGTH_LONG).show();
                } else {
                    // Display the contact data on the UI List using the Adapter
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // A ListView item click transfers the contact info to the New Text screen
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String contactNameAndNumber = (String) parent.getItemAtPosition(position);
                // Clean the phone number and add it to the New Text screen
                String phoneSelected = getCleanPhoneNumber(contactNameAndNumber);
                NewTextFragment.setPhoneInput(phoneSelected);
                // Close this dialog fragment to return to the New Text screen
                getFragmentManager().beginTransaction()
                        .remove(ContactListDialogFragment.this).commit();
            }
        });
    }

    // Helper method to return contact data based on a search query
    @SuppressLint("Range")
    private List<String> getContacts(String nameSearched) {
        List<String> contacts = new ArrayList<>();
        // Create a cursor and query the contact display data
        ContentResolver cr = getActivity().getContentResolver();
        Cursor contactDisplayCursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                "DISPLAY_NAME LIKE '%" + nameSearched + "%'", null, null);
        contactDisplayCursor.moveToFirst();
        do {
            // Get contact name and ID
            String contactId = contactDisplayCursor.getString(
                    contactDisplayCursor.getColumnIndex(ContactsContract.Contacts._ID));
            String contactName = contactDisplayCursor.getString(
                    contactDisplayCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // Get the contact's associated phone number(s)
            Cursor contactPhoneCursor = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null);
            while (contactPhoneCursor.moveToNext()) {
                String contactNumber = contactPhoneCursor.getString(
                        contactPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(contactName + "; " + contactNumber);
            }
        } while (contactDisplayCursor.moveToNext());
        return contacts;
    }

    // Helper method to remove special chars and letters from a phone number string
    private String getCleanPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^a-zA-Z0-9]", "");
        phoneNumber = phoneNumber.replaceAll("([^0-9.])", "");
        return phoneNumber;
    }
}