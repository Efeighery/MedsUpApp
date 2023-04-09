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

/*
 *  Class name: ContactNoteAdapter.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/ContactNoteAdapter.java
 *
 */

public class ContactNoteAdapter extends FirestoreRecyclerAdapter <ContactInfo, ContactNoteAdapter.NoteViewHolder> {

    // A Context objects is made and declared in the constructor object
    Context context;

    // The Instantiable class is declared as a sort of ArrayList object in the constructor
    public ContactNoteAdapter(@NonNull FirestoreRecyclerOptions<ContactInfo> options, Context context){
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull ContactInfo contactNotes) {
        // With an object of the instantiable class and the NoteViewHolder, the variables are declared and set in their TextView formats
        holder.conTitleView.setText(contactNotes.title);
        holder.conContentView.setText(contactNotes.content);

        // An itemView object is used to combine the two parts of a note as one
        holder.itemView.setOnClickListener(v -> {
            Intent in = new Intent(context, ContactDetails.class);

            in.putExtra("title", contactNotes.title);
            in.putExtra("content", contactNotes.content);

            // An ID String is used to help find a specific note (As a Snapshot of saved data)
            String conDocId = this.getSnapshots().getSnapshot(position).getId();
            in.putExtra("conDocId", conDocId);

            // The Intent object is triggered via the Context object
            context.startActivity(in);
        });
    }

    // This is used for binding notes into the ViewHolder, with the TextViews declared and initialised accordingly
    public class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView conTitleView, conContentView;

        public NoteViewHolder(@NonNull View itemView){
            super(itemView);

            conTitleView = itemView.findViewById(R.id.conNoteTitleView);
            conContentView = itemView.findViewById(R.id.conNoteContentView);
        }
    }

    // The contact note item is used for the View object to help display a note and its contents
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_con_note_item, parent, false);
        return new NoteViewHolder(view);
    }
}
