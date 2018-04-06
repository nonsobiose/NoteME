package com.nonsobiose.noteme;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nonsobiose.noteme.NoteUtils.Note;
import com.nonsobiose.noteme.NoteUtils.NoteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteDatabase noteDatabase;
    private RecyclerView listOfNotes;
    private FloatingActionButton addNoteFab;
    private ImageView emptyStateImage;
    private TextView emptyStateText;
    private NoteAdapter noteAdapter;
    private NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getNoteDatabase(this);

        listOfNotes = findViewById(R.id.list_of_notes);
        addNoteFab = findViewById(R.id.add_note_fab);
        emptyStateImage = findViewById(R.id.empty_state_image);
        emptyStateText = findViewById(R.id.empty_state_text);

        noteAdapter = new NoteAdapter(new ArrayList<>(), this);
        listOfNotes.setAdapter(noteAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listOfNotes.setLayoutManager(layoutManager);
        //layoutManager.setReverseLayout(true);

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getLayoutPosition();
                View clickedNote = listOfNotes.getLayoutManager().findViewByPosition(position);
                int noteId = (Integer) clickedNote.getTag();

                Note note = Note.deleteNote(noteId, noteDatabase);

                Snackbar snackbar = Snackbar.make(findViewById(R.id.constraint_layout), "Note was deleted", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", view -> noteDatabase.noteDao().insert(note));
                snackbar.show();
                noteAdapter.notifyItemRemoved(position);
            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(listOfNotes);


        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.notes.observe(this, notes -> {
            if (notes.size() != 0) {
                noteAdapter.swapAdapter(notes);
                listOfNotes.smoothScrollToPosition(notes.size());
            } else {
                listOfNotes.setVisibility(View.INVISIBLE);
                emptyStateImage.setVisibility(View.VISIBLE);
                emptyStateText.setVisibility(View.VISIBLE);
            }
        });

        addNoteFab.setOnClickListener(view -> startActivity(new Intent(this, NewNoteActivity.class)));

        noteAdapter.setOnNoteClickListener(position -> {
            View clickedNote = listOfNotes.getLayoutManager().findViewByPosition(position);
            int noteId = (Integer) clickedNote.getTag();
            Intent noteIntent = new Intent(this, EditNoteActivity.class);
            noteIntent.putExtra("note_id", noteId);
            startActivity(noteIntent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_notes:
                List<Note> notes = noteDatabase.noteDao().loadNotess();
                noteDatabase.noteDao().deleteNotes(notes);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
