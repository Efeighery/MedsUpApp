package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, MainActivity.class)));

        emailBtn = findViewById(R.id.emailBtn);
        emailBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, EmailMaker.class)));

        smsBtn = findViewById(R.id.textBtn);
        smsBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, TextMaker.class)));

        addCon = findViewById(R.id.addBtn);
        addCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogAddContact viewDialogAddContact = new ViewDialogAddContact();
                viewDialogAddContact.ShowDialog(AddContact.this);
            }
        });
        
        displayContacts();

        
    }

    private void displayContacts() {
        databaseReference.child("CONTACTS").orderByChild("cID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactItemArrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ContactItem contactItem = dataSnapshot.getValue(ContactItem.class);
                    contactItemArrayList.add(contactItem);
                }

                adapter = new ContactRecyclerAdapter(AddContact.this, contactItemArrayList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAddContact{
        public void ShowDialog(Context context){
            final Dialog dia = new Dialog(context);

            dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia.setCancelable(false);

            dia.setContentView(R.layout.alert_dialog_add_new_contact);

            EditText cName = dia.findViewById(R.id.conName);
            EditText cEmail = dia.findViewById(R.id.conEmailAdd);
            EditText cPhone = dia.findViewById(R.id.conTelephone);

            Button addBotn = dia.findViewById(R.id.addCon);
            Button exitBtn = dia.findViewById(R.id.cancelAction);

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dia.dismiss();
                }
            });

            addBotn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cID = "Contact"+ new Date().getTime();
                    String name = cName.getText().toString();
                    String email = cEmail.getText().toString();
                    String phone = cPhone.getText().toString();

                    if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                        Toast.makeText(context, "All fields have to be filled in to save a contact entry", Toast.LENGTH_LONG).show();
                    }
                    else{
                        databaseReference.child("CONTACTS").child(cID).setValue(new ContactItem(cID, name, email, phone));
                        Toast.makeText(context, "And....Voila!", Toast.LENGTH_LONG).show();
                        dia.dismiss();
                    }
                }
            });

            dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dia.show();
        }
    }
}