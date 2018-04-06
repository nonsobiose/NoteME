package com.nonsobiose.noteme;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.nonsobiose.noteme.NoteUtils.Note;

import org.w3c.dom.Text;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    private TextView noteDateDisplay;
    private TextView noteMessageDisplay;
    private FloatingActionButton editNoteFab;

    private int noteId;
    private String noteMessage;

    private EditNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteDateDisplay = findViewById(R.id.note_date);
        noteMessageDisplay = findViewById(R.id.note_view);
        editNoteFab  = findViewById(R.id.edit_note_fab);

        Intent noteIntent = getIntent();
        noteId = noteIntent.getIntExtra("note_id", -1);

        if(noteId != -1) {
            viewModel = ViewModelProviders.of(this, new Factory(this.getApplication(), noteId)).get(EditNoteViewModel.class);
            viewModel.note.observe(this, note -> {
                noteMessage = note.message;
                noteDateDisplay.setText(note.date);
                noteMessageDisplay.setText(note.message);
            });
        }

        editNoteFab.setOnClickListener(view -> {
            Intent newNoteIntent = new Intent(this, NewNoteActivity.class);
            newNoteIntent.putExtra("note_message", noteMessage);
            newNoteIntent.putExtra("note_id", noteId);
            startActivity(newNoteIntent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
