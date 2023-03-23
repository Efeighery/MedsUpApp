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
 *  Class name: AddMedication.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 12/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/AddMedication.java
 */

public class AddMedication extends AppCompatActivity {
    // A Database Reference is used to help save a diagnosis entry into the user table
    DatabaseReference databaseReference;

    // This will produce a list of saved Diagnosis entries
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    String userID;

    // An ArrayList object will be made from the instantiable class
    ArrayList<MedicationItem> medicationItemArrayList;

    // This is used to update the Diagnosis list when an entry is either updated or deleted
    MedicationRecyclerAdapter adapter;

    // The buttons will be initialised and declared here
    Button addMeds, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        // The Firebase Database object is declared in here as is the RecyclerView
        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("mID");


        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // The ArrayList object is also declared in here
        medicationItemArrayList = new ArrayList<>();

        // If the user clicks the Home button, they will leave the AddMedication page to go back to the Home page
        addMeds = findViewById(R.id.addMedBtn);
        homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddMedication.this, MainActivity.class);
            startActivity(intent);
        });

        // The AddMedication Button will be used to help add a new Diagnosis entry into the Diagnosis List
        addMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The object of this method is used to trigger the actual method to save a diagnosis entry
                ViewAddDialogMed viewAddDialogMed = new ViewAddDialogMed();
                viewAddDialogMed.showDialog(AddMedication.this);
            }
        });

        // If the medication list has had some entries saved, this method will check and display them to the user
        readData();
    }

    private void readData() {
        // Here, the Database object will cycle through the Medication table and use an ID to help sort out the saved entries to display them in the list

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();

        databaseReference.child("MEDICATIONS").orderByChild("mID").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The ArrayList object is needed to get the table entries
                medicationItemArrayList.clear();

                // A DataSnapshot will take an instance that contains data and is granted access
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // A 2nd ArrayList object is used to grab the data instance from the instantiable class and add them to the list
                    MedicationItem medicationItem = dataSnapshot.getValue(MedicationItem.class);

                    medicationItemArrayList.add(medicationItem);
                }
                // The Adapter will then be set to the Recycler View and will update the Diagnosis List whether or not data is updated, added or removed
                adapter = new MedicationRecyclerAdapter(AddMedication.this, medicationItemArrayList);
                recyclerView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewAddDialogMed{
        public void showDialog(Context context){
            // The Dialog object helps for adding a new medication to the RecyclerView List
            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_medication);

            // Using the dialogue object to use the XML file which allows for adding new medications

            // The Buttons and Text fields will be initialised and declared here by their ids.
            EditText medName = dialog.findViewById(R.id.medName);
            EditText medRules = dialog.findViewById(R.id.medRules);

            Button addEntry = dialog.findViewById(R.id.addMed);
            Button cancelEntry = dialog.findViewById(R.id.cancelAction);

            addEntry.setText("ADD MEDICATION ENTRY");

            // If the user wants to leave, the addMedication dialog object method will be cancelled
            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // Here, the add Medication method will allow for a new contact to be added to the database
            addEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // With an id, to signify when the medication was added while the other variable will be acquired and added to the Firebase database
                    String mID = "Name: "+new Date().getTime();
                    String name = medName.getText().toString();
                    String rules = medRules.getText().toString();

                    // If the user hasn't filled one or all text fields needed to save a diagnosis, a notification will warn the user to enter something for the medication to be saved
                    if(name.isEmpty() || rules.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Otherwise, the medication table will use the id as a child node to help set the newly made medication into the medication table and the user will notify the user
                        databaseReference.child("MEDICATIONS").child(mID).setValue(new MedicationItem(mID, name, rules));
                        Toast.makeText(context, "Entry added", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}