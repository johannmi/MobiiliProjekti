package com.example.mobiiliprojekti;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private Context mCtx;
    private List<Notes> notesList;

    public NotesAdapter(Context mCtx, List<Notes> notesList) {
        this.mCtx = mCtx;
        this.notesList = notesList;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.note_layout, null);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        holder.textViewDate.setText(notes.getDate());
        holder.textViewTime.setText(notes.getTime());
        holder.textViewMood.setText(notes.getMood());
        holder.textViewDone.setText(notes.getDone());
        holder.textViewNotes.setText(notes.getNotes());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(notes.getImage()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewDate, textViewTime, textViewMood, textViewDone, textViewNotes;

        public NotesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewMood = itemView.findViewById(R.id.textViewMood);
            textViewDone = itemView.findViewById(R.id.textViewDone);
            textViewNotes = itemView.findViewById(R.id.textViewNote);

        }
    }
}
