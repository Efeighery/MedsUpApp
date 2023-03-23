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

    // A Database Reference is used to help save a contact entry into the user table
    DatabaseReference databaseReference;

    // This will produce a list of saved contact entries
    RecyclerView recyclerView;

    String userID;
    FirebaseUser firebaseUser;

    // An ArrayList object will be made from the instantiable class
    ArrayList<ContactItem> contactItemArrayList;

    // This is used to update the Contact list when an entry is either updated or deleted
    ContactRecyclerAdapter adapter;

    // The buttons will be initialised and declared here
    Button addCon, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // The Firebase Database object is declared in here as is the RecyclerView
        databaseReference = FirebaseDatabase.getInstance().getReference().child("cID");

        recyclerView = findViewById(R.id.reView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // The ArrayList object is also declared in here
        contactItemArrayList = new ArrayList<>();

        // If the user clicks the Home button, they will leave the AddContact page to go back to the Home page
        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AddContact.this, MainActivity.class)));

        // The AddContact Button will be used to help add a new Contact entry into the Contact List
        addCon = findViewById(R.id.addBtn);
        addCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The object of this method is used to trigger the actual method to save a contact entry
                ViewDialogAddContact viewDialogAddContact = new ViewDialogAddContact();
                viewDialogAddContact.ShowDialog(AddContact.this);
            }
        });

        // If the contact list has had some entries saved, this method will check and display them to the user
        displayContacts();

        
    }

    private void displayContacts() {
        // Here, the Database object will cycle through the Contact table and use an ID to help sort out the saved entries to display them in the list

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();

        databaseReference.child("CONTACTS").orderByChild("cID").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // The ArrayList object is needed to get the table entries
                contactItemArrayList.clear();

                // A DataSnapshot will take an instance that contains data and is granted access
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    // A 2nd ArrayList object is used to grab the data instance from the instantiable class and add them to the list
                    ContactItem contactItem = dataSnapshot.getValue(ContactItem.class);
                    contactItemArrayList.add(contactItem);
                }

                // The Adapter will then be set to the Recycler View and will update the Contact List whether or not data is updated, added or removed
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

            // The Dialog object helps for adding a new contact to the RecyclerView List
            final Dialog dia = new Dialog(context);

            dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia.setCancelable(false);

            dia.setContentView(R.layout.alert_dialog_add_new_contact);

            // Using the dialogue object to use the XML file which allows for adding new contacts

            // The Buttons and Text fields will be initialised and declared here by their ids.

            EditText cName = dia.findViewById(R.id.conName);
            EditText cEmail = dia.findViewById(R.id.conEmailAdd);
            EditText cPhone = dia.findViewById(R.id.conTelephone);

            Button addBotn = dia.findViewById(R.id.addCon);
            Button exitBtn = dia.findViewById(R.id.cancelAction);

            // If the user wants to leave, the addContact dialog object method will be cancelled
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dia.dismiss();
                }
            });

            // Here, the add Contact method will allow for a new contact to be added to the database
            addBotn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // With an id, to signify when the contact was added while the other 3 variables will be acquired and added to the Firebase database
                    String cID = "Contact"+ new Date().getTime();
                    String name = cName.getText().toString();
                    String email = cEmail.getText().toString();
                    String phone = cPhone.getText().toString();

                    // If the user hasn't filled one or all text fields needed to save a contact, a notification will warn the user to enter something for the contact to be saved
                    if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                        Toast.makeText(context, "All fields have to be filled in to save a contact entry", Toast.LENGTH_LONG).show();
                    }
                    else{
                        // Otherwise, the contact table will use the id as a child node to help set the newly made contact into the contact table and the user will notify the user
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