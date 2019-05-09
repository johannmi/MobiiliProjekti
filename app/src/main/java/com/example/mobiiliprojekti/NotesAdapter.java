package com.example.mobiiliprojekti;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for the RecyclerView to present the journal entries in CardViews
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private Context mCtx;
    private List<Notes> notesList;

    /**
     *
     * @param mCtx Needed to create layout inflater
     * @param notesList List of data from the database stored in a Note class
     */

    public NotesAdapter(Context mCtx, List<Notes> notesList) {
        this.mCtx = mCtx;
        this.notesList = notesList;
    }

    /**
     * Inflates the note layout of a RecyclerView item
     * and passes the view to NotesViewHolder and
     * Creates a view holder instance
     * @param viewGroup viewGroup
     * @param i i
     * @return Returns NotesViewHolder instance
     */

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.note_layout, null);
        return new NotesViewHolder(view);
    }

    /**
     * Binds data to the UI-elements
     * Gets data from the list of Notes
     * @param holder NotesViewHolder
     * @param position Position of a Note object in a list
     */

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Notes notes = notesList.get(position);
        holder.textViewDate.setText(notes.getDate());
        holder.textViewTime.setText(notes.getTime());
        holder.textViewMood.setText(notes.getMood());
        holder.textViewDone.setText(notes.getDone());
        holder.textViewNotes.setText(notes.getNotes());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(notes.getImage()));

        // Sets color depending on mood
        if (notes.getMood().equals("Erinomainen")) {
            holder.textViewMood.setTextColor(ContextCompat.getColor(mCtx,R.color.mood5));
        } else if (notes.getMood().equals("Hyvä")) {
            holder.textViewMood.setTextColor(ContextCompat.getColor(mCtx,R.color.mood4));
        } else if (notes.getMood().equals("Neutraali")) {
            holder.textViewMood.setTextColor(ContextCompat.getColor(mCtx,R.color.mood3));
        }  else if (notes.getMood().equals("Huono")) {
            holder.textViewMood.setTextColor(ContextCompat.getColor(mCtx,R.color.mood2));
        }  else if (notes.getMood().equals("Kamala")) {
            holder.textViewMood.setTextColor(ContextCompat.getColor(mCtx,R.color.mood1));
        }

    }

    /**
     * Get item count
     * @return Returns size of the list
     */

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * View holder for the journal entries
     * Finds the views so data can be added to them
     */

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
