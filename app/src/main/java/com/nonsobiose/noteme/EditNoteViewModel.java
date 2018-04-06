package com.nonsobiose.noteme;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.nonsobiose.noteme.NoteUtils.Note;
import com.nonsobiose.noteme.NoteUtils.NoteDatabase;

/**
 * Created by Chidiebere on 3/22/2018.
 */

public class EditNoteViewModel extends AndroidViewModel {
    private NoteDatabase noteDatabase;
    public LiveData<Note> note;

    public EditNoteViewModel(@NonNull Application application, int id) {
        super(application);
        noteDatabase = NoteDatabase.getNoteDatabase(this.getApplication());
        note = noteDatabase.noteDao().loadNote(id);
    }

}
