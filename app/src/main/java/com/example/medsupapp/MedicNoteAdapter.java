package com.example.medsupapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MedicNoteAdapter extends FirestoreRecyclerAdapter <MedicNotes, MedicNoteAdapter.NoteViewHolder>{
    Context context;

    public MedicNoteAdapter(@NonNull FirestoreRecyclerOptions<MedicNotes> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull MedicNotes medicNotes) {
        holder.medTitleView.setText(medicNotes.title);
        holder.medContentView.setText(medicNotes.content);

        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, MedDetails.class);
            in.putExtra("title", medicNotes.title);
            in.putExtra("content", medicNotes.content);

            String medDocId = this.getSnapshots().getSnapshot(position).getId();
            in.putExtra("medDocId", medDocId);

            context.startActivity(in);
        });

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_med_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView medTitleView, medContentView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            medTitleView = itemView.findViewById(R.id.mediNoteTitleView);
            medContentView = itemView.findViewById(R.id.mediNoteContentView);
        }
    }
}
