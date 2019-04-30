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
        holder.textViewTitle.setText(notes.getTitle());
        holder.textViewDesc.setText(notes.getDesc());
        holder.textViewRating.setText(String.valueOf(notes.getRating()));
        holder.textViewPrice.setText(String.valueOf(notes.getPrice()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(notes.getImage()));
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;

        public NotesViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
