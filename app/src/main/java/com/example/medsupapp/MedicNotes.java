package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

/*
 *  Class name: MedicNotes.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 30/03/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/MedicationNotes.java
 *
 */



public class MedicNotes extends AppCompatActivity {

    // Two Floating Action buttons; one for adding notes and one for going back to the home page
    FloatingActionButton addNoteBtn, homePager;

    // A RecyclerView object used to display a list of previously saved/edited notes
    RecyclerView reVe;

    // A Menu button for decoration
    ImageButton menuBtn;

    // The Adapter object that's used to house a note object
    MedicNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_notes);

        // The previously declared XML variables are initialised and recalibrated with their IDs
        addNoteBtn = findViewById(R.id.addNote);
        homePager = findViewById(R.id.homeBtn);

        reVe = findViewById(R.id.recyclerView);
        menuBtn = findViewById(R.id.menuMedButton);

        // When a user doesn't want to make a note, they can click the home button to go back to the main page
        // When a user clicks the button to add a note, they'll be taken to the page that lets them do that
        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(MedicNotes.this, MedicDetails.class)));
        homePager.setOnClickListener(v -> startActivity(new Intent(MedicNotes.this, MainActivity.class)));

        // A method that's used to set up the RecyclerView for all saved notes
        setUpRecycleView();
    }


    void setUpRecycleView(){
        // A query is used to listen into a utility class tailor made to assist in CRUD functionalities
        // The method declared in ContactBox will order the saved the notes by their titles in ascending order
        Query query = MedicBox.getCollectionRefForMedicalNotes().orderBy("title", Query.Direction.ASCENDING);

        // The instantiable class is declared in a FireStore Recycler object and the declared query will be confined with
        // the instantiable object to help create the RecyclerView and display the saved notes
        FirestoreRecyclerOptions<MedicInfo> options = new FirestoreRecyclerOptions.Builder<MedicInfo>().setQuery(query, MedicInfo.class).build();

        // The LayoutManager will be declared within the RecyclerView object
        reVe.setLayoutManager(new LinearLayoutManager(this));

        // And the Adapter file will be declared with the RecycleOptions object
        adapter = new MedicNoteAdapter(options, this);
        reVe.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}