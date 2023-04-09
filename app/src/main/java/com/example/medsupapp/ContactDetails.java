package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

/*
 *  Class name: ContactDetails.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/ContactDetails.java
 *
 */

public class ContactDetails extends AppCompatActivity {

    // The XML variables are declared here
    EditText editConTitle, editConContent;
    ImageButton saveConNote;
    TextView pageConTitle;
    TextView deleteConNote;

    // A Boolean flag that helps to act as an indicator for when a user is editing a saved note
    boolean inEditMode = false;

    // Used to get the String variables for a note and to track down the correct notes specified by a user
    String title, content, conDocId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        // The XML variables are initialised by their IDs
        editConTitle = findViewById(R.id.conTitle);
        editConContent = findViewById(R.id.conInfo);
        saveConNote = findViewById(R.id.saveNoteButton);
        pageConTitle = findViewById(R.id.pageTitle);
        deleteConNote = findViewById(R.id.removeConNote);

        // This will grab the notes saved by a user
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        conDocId = getIntent().getStringExtra("conDocId");

        // If a note ID isn't empty, then the Boolean flag will be set as true (The user is currently in Editing Note Mode)
        if(conDocId != null && !conDocId.isEmpty()){
            inEditMode = true;
        }

        // The Note Variables are set here
        editConTitle.setText(title);
        editConContent.setText(content);

        // If the user is currently editing a note, then the delete button will be made visible
        if(inEditMode){
            pageConTitle.setText("Edit your note");
            deleteConNote.setVisibility(View.VISIBLE);
        }

        // This button triggers a method used to save notes to Firestore
        saveConNote.setOnClickListener(v -> saveContactNote());

        // This button triggers a method used to delete a note from Firestore
        deleteConNote.setOnClickListener(v -> deleteConNoteFromFireStore());
    }

    void saveContactNote(){
        // The note title and content is acquired here and used to save a new note
        String contactNoteTitle = editConTitle.getText().toString();
        String contactNoteContent = editConContent.getText().toString();

        // If the note's title hasn't been filled in yet, this message will be triggered
        if(contactNoteTitle == null || contactNoteTitle.isEmpty()){
            editConTitle.setError("Title is needed to save the note");
            return;
        }

        // The instantiable class is called in a  new object that will contain the String variables declared up above
        ContactInfo contactNotes = new ContactInfo();
        contactNotes.setTitle(contactNoteTitle);
        contactNotes.setContent(contactNoteContent);

        // The object will also automatically trigger another method to save the note to the database
        saveConNoteToFireStore(contactNotes);
    }

    void saveConNoteToFireStore(ContactInfo contactsNotes){
        // An object of the FireStore database used for saving notes is declared here
        DocumentReference documentReference;

        // If the user is editing a note, the boolean flag will lead to the utility file finding the edited note via an ID
        if(inEditMode){
            documentReference = ContactBox.getCollectionRefForContactNotes().document(conDocId);
        }
        // Otherwise, if it's a new note, then the same will trigger but a new ID is generated if a note is saved
        else{
            documentReference = ContactBox.getCollectionRefForContactNotes().document();
        }

        // Then this will trigger an if else method for saving a newly created note in FireStore
        documentReference.set(contactsNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // If all goes according to plan, then the note will be saved to the FireStore database
                if(task.isSuccessful()){
                    ContactBox.showToast(ContactDetails.this, "Contact note has been added");
                    finish();
                }
                else{
                    // Or if an error happens mid-save, this message will appear
                    ContactBox.showToast(ContactDetails.this, "An error occurred");

                }
            }
        });
    }

    // This is used to help remove a specified note from the database
    void deleteConNoteFromFireStore(){
        // Once again, the FireStore database object is created and the ID object is used to find a requested note
        DocumentReference documentReference;
        documentReference = ContactBox.getCollectionRefForContactNotes().document(conDocId);

        // This if else statement will remove the note
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // If all goes according to plan, then the note will be removed to the FireStore database
                if(task.isSuccessful()){
                    ContactBox.showToast(ContactDetails.this, "Contact note has been deleted");
                    finish();
                }
                // Otherwise, if an error happens mid-removal, this message will appear
                else{
                    ContactBox.showToast(ContactDetails.this, "Error has occurred!");
                }
            }
        });
    }
}