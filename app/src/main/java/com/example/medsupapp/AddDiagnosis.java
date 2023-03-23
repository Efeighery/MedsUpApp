package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;


/*
 *  Class name: AddDiagnosis.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 14/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/AddDiagnosis.java
 *
 */

public class AddDiagnosis extends AppCompatActivity {


    // A Database Reference is used to help save a diagnosis entry into the user table
    DatabaseReference databaseReference;

    // This will produce a list of saved Diagnosis entries
    RecyclerView recyclerView;

    String userID;

    FirebaseUser firebaseUser;

    // An ArrayList object will be made from the instantiable class
    ArrayList<DiagnosisItem> diagnosisItemArrayList;

    // This is used to update the Diagnosis list when an entry is either updated or deleted
    DiagnosisRecyclerAdapter adapter;

    // The buttons will be initialised and declared here
    Button addBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diagnosis);

        // The Firebase Database object is declared in here as is the RecyclerView
        Objects.requireNonNull(getSupportActionBar()).hide();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("hID");

        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // The ArrayList object is also declared in here
        diagnosisItemArrayList = new ArrayList<>();

        // If the user clicks the Home button, they will leave the AddDiagnosis page to go back to the Home page
        homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddDiagnosis.this, MainActivity.class);
            startActivity(intent);
        });

        // The AddDiagnosis Button will be used to help add a new Diagnosis entry into the Diagnosis List
        addBtn = findViewById(R.id.addDiagBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The object of this method is used to trigger the actual method to save a diagnosis entry
                ViewDialogAddDiag viewDialogAddDiag = new ViewDialogAddDiag();
                viewDialogAddDiag.showDialog(AddDiagnosis.this);
            }
        });

        // If the diagnosis list has had some entries saved, this method will check and display them to the user
        findData();

    }

    private void findData() {
        // Here, the Database object will cycle through the Diagnosis table and use an ID to help sort out the saved entries to display them in the list

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();


        databaseReference.child("CONDITIONS").orderByChild("CONDITIONS").orderByChild("hID").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The ArrayList object is needed to get the table entries
                diagnosisItemArrayList.clear();

                // A DataSnapshot will take an instance that contains data and is granted access
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // A 2nd ArrayList object is used to grab the data instance from the instantiable class and add them to the list
                    DiagnosisItem diagnosisItem = dataSnapshot.getValue(DiagnosisItem.class);

                    diagnosisItemArrayList.add(diagnosisItem);
                }
                // The Adapter will then be set to the Recycler View and will update the Diagnosis List whether or not data is updated, added or removed
                adapter = new DiagnosisRecyclerAdapter(AddDiagnosis.this, diagnosisItemArrayList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAddDiag{
        public void showDialog (Context context){
            // The Dialog object helps for adding a new diagnosis to the RecyclerView List
            final Dialog dialogue = new Dialog(context);
            dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogue.setCancelable(false);
            dialogue.setContentView(R.layout.alert_dialog_add_new_health);

            // Using the dialogue object to use the XML file which allows for adding new diagnoses

            // The Buttons and Text fields will be initialised and declared here by their ids.

            EditText diagnosisName = dialogue.findViewById(R.id.healthName);
            Button addDiagnosis = dialogue.findViewById(R.id.addHeal);
            Button leaveBtn = dialogue.findViewById(R.id.cancelAction);

            // If the user wants to leave, the addDiagnosis dialog object method will be cancelled
            leaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogue.dismiss();
                }
            });

            // Here, the add Diagnosis method will allow for a new contact to be added to the database
            addDiagnosis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // With an id, to signify when the diagnosis was added while the other variable will be acquired and added to the Firebase database
                    String hID = "Diagnosis"+new Date().getTime();
                    String term = diagnosisName.getText().toString();

                    // If the user hasn't filled one or all text fields needed to save a diagnosis, a notification will warn the user to enter something for the diagnosis to be saved
                    if(term.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Otherwise, the diagnosis table will use the id as a child node to help set the newly made diagnosis into the diagnoses table and the user will notify the user
                        databaseReference.child("CONDITIONS").child(hID).setValue(new DiagnosisItem(hID, term));
                        Toast.makeText(context, "Entry saved!", Toast.LENGTH_SHORT).show();
                        dialogue.dismiss();
                    }
                }
            });
            dialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogue.show();
        }
    }
}