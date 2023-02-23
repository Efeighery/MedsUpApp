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
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<MedicationItem> medicationItemArrayList;

    MedicationRecyclerAdapter adapter;

    Button addMeds, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicationItemArrayList = new ArrayList<>();

        addMeds = findViewById(R.id.addMedBtn);
        homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMedication.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAddDialogMed viewAddDialogMed = new ViewAddDialogMed();
                viewAddDialogMed.showDialog(AddMedication.this);
            }
        });

        readData();
    }

    private void readData() {
        databaseReference.child("MEDICATIONS").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                medicationItemArrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicationItem medicationItem = dataSnapshot.getValue(MedicationItem.class);

                    medicationItemArrayList.add(medicationItem);
                }
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
            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_medication);

            EditText medName = dialog.findViewById(R.id.medName);
            EditText medRules = dialog.findViewById(R.id.medRules);

            Button addEntry = dialog.findViewById(R.id.addMed);
            Button cancelEntry = dialog.findViewById(R.id.cancelAction);

            addEntry.setText("ADD MEDICATION ENTRY");

            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            addEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mID = "Name: "+new Date().getTime();
                    String name = medName.getText().toString();
                    String rules = medRules.getText().toString();

                    if(name.isEmpty() || rules.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
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