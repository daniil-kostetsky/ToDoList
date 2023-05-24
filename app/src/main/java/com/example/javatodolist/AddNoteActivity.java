package com.example.javatodolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextEnterNote;
    private RadioButton radioButtonLowPriority;
    private RadioButton radioButtonMediumPriority;
    private RadioButton radioButtonHighPriority;
    private Button buttonSaveValue;

    private AddNoteViewModel addNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_note);
        addNoteViewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                }
            }
        });
        initViews();


        buttonSaveValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String text = editTextEnterNote.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter text!", Toast.LENGTH_SHORT).show();
        }
        int priority = getPriority();
        Note note = new Note(text, priority);

        addNoteViewModel.saveNote(note);
    }

    private int getPriority() {
        int priority = -1;
        if (radioButtonLowPriority.isChecked()) {
            priority = 0;
        } else if (radioButtonMediumPriority.isChecked()) {
            priority = 1;
        } else if (radioButtonHighPriority.isChecked()) {
            priority = 2;
        } else {
            Toast.makeText(this, "Choose priority!", Toast.LENGTH_SHORT).show();
        }
        return priority;
    }

    private void initViews() {

        editTextEnterNote = findViewById(R.id.editTextEnterNote);

        radioButtonLowPriority = findViewById(R.id.radioButtonLowPriority);
        radioButtonMediumPriority = findViewById(R.id.radioButtonMediumPriority);
        radioButtonHighPriority = findViewById(R.id.radioButtonHighPriority);

        buttonSaveValue = findViewById(R.id.buttonSaveValue);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteActivity.class);
    }
}