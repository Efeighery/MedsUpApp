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
 *  Class name: DiagnosesNotes.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/DiagnosesNotes.java
 *
 */

public class DiagnosesNotes extends AppCompatActivity {

    FloatingActionButton addNoteBtn, homePager;
    RecyclerView reVe;

    ImageButton menuBtn;
    DiagNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnoses_notes);

        addNoteBtn = findViewById(R.id.addNote);
        homePager = findViewById(R.id.homeBtn);

        reVe = findViewById(R.id.recyclerView);
        menuBtn = findViewById(R.id.menuMedButton);

        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(DiagnosesNotes.this, DiagnosisDetails.class)));
        homePager.setOnClickListener(v -> startActivity(new Intent(DiagnosesNotes.this, MainActivity.class)));


        setUpRecycleView();
    }

    void setUpRecycleView(){
        Query query = DiagBox.getCollectionRefForDiagnosisNotes().orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<DiagnosisInfo> options = new FirestoreRecyclerOptions.Builder<DiagnosisInfo>()
                .setQuery(query, DiagnosisInfo.class).build();

        reVe.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DiagNoteAdapter(options, this);
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