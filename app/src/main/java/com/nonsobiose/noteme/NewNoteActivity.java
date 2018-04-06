package com.nonsobiose.noteme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.nonsobiose.noteme.NoteUtils.Note;
import com.nonsobiose.noteme.NoteUtils.NoteDatabase;

import java.util.Date;

public class NewNoteActivity extends AppCompatActivity {

    private EditText noteInput;
    private NoteDatabase noteDatabase;

    private int noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        noteInput = findViewById(R.id.note_input_edittext);
        noteDatabase = NoteDatabase.getNoteDatabase(this);

        Intent noteIntent = getIntent();
        if (noteIntent != null) {
            String noteMessage = noteIntent.getStringExtra("note_message");
            noteId = noteIntent.getIntExtra("note_id", -1);

            noteInput.setText(noteMessage);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                String message = noteInput.getText().toString().trim();
                Note.createEditNote(this, message, noteId, noteDatabase);
                finish();
                break;
            case R.id.cancel_note:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
