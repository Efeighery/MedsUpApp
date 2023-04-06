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

public class ContactNotes extends AppCompatActivity {

    FloatingActionButton addNoteBtn, homePager;
    RecyclerView reVe;

    ImageButton menuBtn;
    ContactNoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_notes);

        addNoteBtn = findViewById(R.id.addNote);
        homePager = findViewById(R.id.homeBtn);

        reVe = findViewById(R.id.recyclerView);
        menuBtn = findViewById(R.id.menuMedButton);

        addNoteBtn.setOnClickListener(v -> startActivity(new Intent(ContactNotes.this, ContactDetails.class)));
        homePager.setOnClickListener(v -> startActivity(new Intent(ContactNotes.this, MainActivity.class)));


        setUpRecycleView();
    }

    void setUpRecycleView(){
        Query query = ContactBox.getCollectionRefForContactNotes().orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ContactInfo> options = new FirestoreRecyclerOptions.Builder<ContactInfo>()
                .setQuery(query, ContactInfo.class).build();

        reVe.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ContactNoteAdapter(options, this);
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