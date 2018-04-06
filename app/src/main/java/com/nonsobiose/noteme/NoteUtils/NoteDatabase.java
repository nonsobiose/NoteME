package com.nonsobiose.noteme.NoteUtils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Chidiebere on 3/22/2018.
 */

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase NOTE_DATABASE_INSTANCE;

    public abstract NoteDao noteDao();

    public static NoteDatabase getNoteDatabase(Context context) {
        if(NOTE_DATABASE_INSTANCE == null) {
            NOTE_DATABASE_INSTANCE = Room.databaseBuilder(context, NoteDatabase.class, "Note Database").allowMainThreadQueries().build();
        }
        return NOTE_DATABASE_INSTANCE;
    }

}
