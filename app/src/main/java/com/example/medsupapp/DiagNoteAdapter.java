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

public class DiagNoteAdapter extends FirestoreRecyclerAdapter <DiagnosisInfo, DiagNoteAdapter.NoteViewHolder> {

    Context context;

    public DiagNoteAdapter(@NonNull FirestoreRecyclerOptions<DiagnosisInfo> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull DiagnosisInfo diagNotes) {
        holder.diaTitleView.setText(diagNotes.title);
        holder.diaContentView.setText(diagNotes.content);

        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, ContactDetails.class);

            in.putExtra("title", diagNotes.title);
            in.putExtra("content", diagNotes.content);

            String diaDocId = this.getSnapshots().getSnapshot(position).getId();
            in.putExtra("diaDocId", diaDocId);

            context.startActivity(in);
        });
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView diaTitleView, diaContentView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            diaTitleView = itemView.findViewById(R.id.diagNoteTitleView);
            diaContentView = itemView.findViewById(R.id.diagNoteContentView);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_dia_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
