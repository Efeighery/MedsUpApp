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
 *  Class name: AddHealth.java
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
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/AddHealth.java
 *
 */

public class AddDiagnosis extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<DiagnosisItem> diagnosisItemArrayList;

    DiagnosisRecyclerAdapter adapter;

    Button addBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diagnosis);

        Objects.requireNonNull(getSupportActionBar()).hide();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        diagnosisItemArrayList = new ArrayList<>();

        homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDiagnosis.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addBtn = findViewById(R.id.addDiagBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogAddDiag viewDialogAddDiag = new ViewDialogAddDiag();
                viewDialogAddDiag.showDialog(AddDiagnosis.this);
            }
        });

        findData();

    }

    private void findData() {
        databaseReference.child("CONDITIONS").orderByChild("Diagnoses").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                diagnosisItemArrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DiagnosisItem diagnosisItem = dataSnapshot.getValue(DiagnosisItem.class);

                    diagnosisItemArrayList.add(diagnosisItem);
                }
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
            final Dialog dialogue = new Dialog(context);
            dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogue.setCancelable(false);
            dialogue.setContentView(R.layout.alert_dialog_add_new_health);

            EditText diagnosisName = dialogue.findViewById(R.id.healthName);
            Button addDiagnosis = dialogue.findViewById(R.id.addHeal);
            Button leaveBtn = dialogue.findViewById(R.id.cancelAction);

            leaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogue.dismiss();
                }
            });

            addDiagnosis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hID = "Diagnosis"+new Date().getTime();
                    String term = diagnosisName.getText().toString();

                    if(term.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
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