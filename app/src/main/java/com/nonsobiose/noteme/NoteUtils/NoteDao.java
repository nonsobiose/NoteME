package com.nonsobiose.noteme.NoteUtils;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Chidiebere on 3/22/2018.
 */

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);

    @Query("SELECT * FROM Note")
    LiveData<List<Note>> loadNotes();

    @Query("SELECT * FROM Note")
    List<Note> loadNotess();

    @Query("Select * FROM Note WHERE id = :id")
    LiveData<Note> loadNote(int id);

    @Query("Select * FROM Note WHERE id = :id")
    Note getNote(int id);

    @Update
    void update(Note note);

    @Delete
    int deleteNote(Note note);

    @Delete
    void deleteNotes(List<Note> notes);
}
