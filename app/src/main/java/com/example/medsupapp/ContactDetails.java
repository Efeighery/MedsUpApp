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

    EditText editConTitle, editConContent;

    ImageButton saveConNote;

    TextView pageConTitle;

    TextView deleteConNote;

    boolean inEditMode = false;

    String title, content, conDocId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        editConTitle = findViewById(R.id.conTitle);
        editConContent = findViewById(R.id.conInfo);
        saveConNote = findViewById(R.id.saveNoteButton);
        pageConTitle = findViewById(R.id.pageTitle);
        deleteConNote = findViewById(R.id.removeConNote);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        conDocId = getIntent().getStringExtra("conDocId");

        if(conDocId != null && !conDocId.isEmpty()){
            inEditMode = true;
        }

        editConTitle.setText(title);
        editConContent.setText(content);

        if(inEditMode){
            pageConTitle.setText("Edit your note");
            deleteConNote.setVisibility(View.VISIBLE);
        }

        saveConNote.setOnClickListener(v -> saveContactNote());
        deleteConNote.setOnClickListener(v -> deleteConNoteFromFireStore());
    }

    void saveContactNote(){
        String contactNoteTitle = editConTitle.getText().toString();
        String contactNoteContent = editConContent.getText().toString();

        if(contactNoteTitle == null || contactNoteTitle.isEmpty()){
            editConTitle.setError("Title is needed to save the note");
            return;
        }

        ContactInfo contactNotes = new ContactInfo();
        contactNotes.setTitle(contactNoteTitle);
        contactNotes.setContent(contactNoteContent);

        saveConNoteToFireStore(contactNotes);
    }

    void saveConNoteToFireStore(ContactInfo contactsNotes){
        DocumentReference documentReference;

        if(inEditMode){
            documentReference = ContactBox.getCollectionRefForContactNotes().document(conDocId);
        }
        else{
            documentReference = ContactBox.getCollectionRefForContactNotes().document();
        }

        documentReference.set(contactsNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ContactBox.showToast(ContactDetails.this, "Contact note has been added");
                    finish();
                }
                else{
                    ContactBox.showToast(ContactDetails.this, "An error occurred");

                }
            }
        });
    }

    void deleteConNoteFromFireStore(){
        DocumentReference documentReference;
        documentReference = ContactBox.getCollectionRefForContactNotes().document(conDocId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    ContactBox.showToast(ContactDetails.this, "Contact note has been deleted");
                    finish();
                }
                else{
                    ContactBox.showToast(ContactDetails.this, "Error has occurred!");
                }
            }
        });
    }
}