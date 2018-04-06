package com.nonsobiose.noteme;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.nonsobiose.noteme.NoteUtils.Note;
import com.nonsobiose.noteme.NoteUtils.NoteDatabase;

import java.util.List;

/**
 * Created by Chidiebere on 3/22/2018.
 */

public class NotesViewModel extends AndroidViewModel {

    public LiveData<List<Note>> notes;
    private NoteDatabase noteDatabase;


    public NotesViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getNoteDatabase(this.getApplication());
        notes = noteDatabase.noteDao().loadNotes();
    }

}
