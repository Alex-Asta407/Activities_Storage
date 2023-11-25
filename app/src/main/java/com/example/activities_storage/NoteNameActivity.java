package com.example.activities_storage;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashSet;

public class NoteNameActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        EditText editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (noteId != -1) {
                    // If noteId is valid, update the title of the existing note
                    MainActivity.notes.set(noteId, String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                    // Save the updated notes to SharedPreferences
                    saveNotesToSharedPreferences(MainActivity.notes);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void saveNotesToSharedPreferences(ArrayList<String> notes) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.activities_storage", Context.MODE_PRIVATE);
        HashSet<String> set = new HashSet<>(notes);
        sharedPreferences.edit().putStringSet("notes", set).apply();
    }
}
