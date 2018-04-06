package com.nonsobiose.noteme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nonsobiose.noteme.NoteUtils.Note;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chidiebere on 3/22/2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private Context context;

    public NoteAdapter(List<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View noteView = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        String[] date = Note.getNoteDate(note.date);

        String month = date[0];
        String day = date[1];
        String year = date[2];
        String monthDay = month + " " + day;

        holder.itemView.setTag(note.id);

        holder.monthDay.setText(monthDay);
        holder.year.setText(year);
        holder.noteTitle.setText(note.message);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void swapAdapter(List<Note> notesList) {
        notes = notesList;
        notifyDataSetChanged();
    }

        /* Code that handles the Bluetooth Device Item Click */

    private OnNoteClickListener listener;

    public interface OnNoteClickListener {
        void onNoteClick(int position);
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    /* Code that handles the Bluetooth Device Item Click */

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView monthDay;
        private TextView year;
        private TextView noteTitle;

        public NoteViewHolder(View itemView) {
            super(itemView);
            this.monthDay = itemView.findViewById(R.id.month_day_display);
            this.year = itemView.findViewById(R.id.year_display);
            this.noteTitle = itemView.findViewById(R.id.note_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onNoteClick(position);
        }
    }
}
