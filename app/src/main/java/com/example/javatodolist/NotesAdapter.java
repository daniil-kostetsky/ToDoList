package com.example.javatodolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnNoteCLickListener onNoteCLickListener;


    public List<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void setOnNoteCLickListener(OnNoteCLickListener onNoteCLickListener) {

        this.onNoteCLickListener = onNoteCLickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false
        );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewNote.setText(note.getText());
        int colorResId;
        switch (note.getPriority()) {
            case 0:
                colorResId = android.R.color.holo_green_light;
                break;
            case 1:
                colorResId = android.R.color.holo_blue_light;
                break;
            default:
                colorResId = android.R.color.holo_red_light;
                break;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.textViewNote.setBackgroundColor(color);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteCLickListener != null) {
                    onNoteCLickListener.onNoteClick(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNote = itemView.findViewById(R.id.textViewNote);
        }
    }

    interface OnNoteCLickListener {
        void onNoteClick(Note note);
    }
}
