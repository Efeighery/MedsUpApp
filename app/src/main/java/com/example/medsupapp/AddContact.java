package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
/*
 *  Class name: AddContact.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 14/12/2022
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/AddContact.java
 *
 */
public class AddContact extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    ArrayList<ContactItem> contactItemArrayList;
    ContactRecyclerAdapter adapter;

    Button addCon, homeBtn, emailBtn, smsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactItemArrayList = new ArrayList<>();

        addCon = findViewById(R.id.addBtn);
        addCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, MainActivity.class)));

        emailBtn = findViewById(R.id.emailBtn);
        emailBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, EmailMaker.class)));

        smsBtn = findViewById(R.id.textBtn);
        smsBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, TextMaker.class)));
    }
}