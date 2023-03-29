package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MedicationNotes extends AppCompatActivity {

    FloatingActionButton addNoteBtn, homePager;
    RecyclerView reVe;
    ImageButton menuBtn;
    MedicNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_notes);

        addNoteBtn = findViewById(R.id.addNote);
        homePager = findViewById(R.id.homeBtn);

        reVe = findViewById(R.id.recyclerView);
        menuBtn = findViewById(R.id.menuMedButton);

        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(MedicationNotes.this, MedDetails.class)));
        homePager.setOnClickListener(v -> startActivity(new Intent(MedicationNotes.this, MainActivity.class)));

        setUpRecycleView();
    }


    void setUpRecycleView(){
        Query query = Box.getCollectionRefForMedicalNotes().orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<MedicNotes> options = new FirestoreRecyclerOptions.Builder<MedicNotes>().setQuery(query, MedicNotes.class).build();

        reVe.setLayoutManager(new LinearLayoutManager(this));

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