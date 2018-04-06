package com.nonsobiose.noteme.NoteUtils;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Chidiebere on 3/22/2018.
 */

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String message;
    public String date;

    @Ignore
    public static String generateNoteDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault());
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    @Ignore
    public static String[] getNoteDate(String date) {
        String[] dateArr = date.split("-");
        return dateArr;
    }

    @Ignore
    public static void createEditNote(Context context, String message, int noteId, NoteDatabase noteDatabase) {

        if (!TextUtils.isEmpty(message)) {
            Note note = new Note();
            note.message = message;
            note.date = Note.generateNoteDate(new Date());
            if (noteId == -1) {
                long rowId = noteDatabase.noteDao().insert(note);
                Toast.makeText(context, "Note Created with id " + rowId, Toast.LENGTH_SHORT).show();
            } else {
                note.id = noteId;
                noteDatabase.noteDao().update(note);
                Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public static Note deleteNote(int id, NoteDatabase noteDatabase) {
        Note note = noteDatabase.noteDao().getNote(id);
        noteDatabase.noteDao().deleteNote(note);
        return note;
    }
}
