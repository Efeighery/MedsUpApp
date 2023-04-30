package com.eoghanf.medsupapp;

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

/*
 *  Class name: DiagNoteAdapter.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 06/04/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/DiagNoteAdapter.java
 *
 */

public class DiagNoteAdapter extends FirestoreRecyclerAdapter <DiagnosisInfo, DiagNoteAdapter.NoteViewHolder> {

    // A Context objects is made and declared in the constructor object
    Context context;


    // The Instantiable class is declared as a sort of ArrayList object in the constructor
    public DiagNoteAdapter(@NonNull FirestoreRecyclerOptions<DiagnosisInfo> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull DiagnosisInfo diagNotes) {
        // With an object of the instantiable class and the NoteViewHolder, the variables are declared and set in their TextView formats
        holder.diaTitleView.setText(diagNotes.title);
        holder.diaContentView.setText(diagNotes.content);

        // An itemView object is used to combine the two parts of a note as one
        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, ContactDetails.class);

            in.putExtra("title", diagNotes.title);
            in.putExtra("content", diagNotes.content);

            // An ID String is used to help find a specific note (As a Snapshot of saved data)
            String diaDocId = this.getSnapshots().getSnapshot(position).getId();
            in.putExtra("diaDocId", diaDocId);

            // The Intent object is triggered via the Context object
            context.startActivity(in);
        });
    }

    // This is used for binding notes into the ViewHolder, with the TextViews declared and initialised accordingly
    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView diaTitleView, diaContentView;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            diaTitleView = itemView.findViewById(R.id.diagNoteTitleView);
            diaContentView = itemView.findViewById(R.id.diagNoteContentView);
        }
    }


    // The diagnosis note item is used for the View object to help display a note and its diagnosis
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_dia_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
