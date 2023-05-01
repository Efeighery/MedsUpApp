package com.example.medsupapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

/*
 *  Class name: MedicDetails.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 30/03/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/MedDetails.java
 *
 */

public class MedicDetails extends AppCompatActivity {

    // The XML variables are declared here
    EditText editMedTitle, editMedContent;
    ImageButton saveMedNote;
    TextView pageMedTitle;
    TextView deleteMedNote;

    // A Boolean flag that helps to act as an indicator for when a user is editing a saved note
    boolean inEditMode = false;

    // Used to get the String variables for a note and to track down the correct notes specified by a user
    String title, content, medDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);

        // The XML variables are initialised by their IDs
        editMedTitle = findViewById(R.id.medTitle);
        editMedContent = findViewById(R.id.medInfo);
        saveMedNote = findViewById(R.id.saveNoteButton);
        pageMedTitle = findViewById(R.id.pageTitle);
        deleteMedNote = findViewById(R.id.removeMedNote);

        // This will grab the notes saved by a user
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        medDocId = getIntent().getStringExtra("medDocId");

        // If a note ID isn't empty, then the Boolean flag will be set as true (The user is currently in Editing Note Mode)
        if(medDocId!= null && !medDocId.isEmpty()){
            inEditMode = true;
        }

        // The Note Variables are set here
        editMedTitle.setText(title);
        editMedContent.setText(content);

        // If the user is currently editing a note, then the delete button will be made visible
        if(inEditMode){
            pageMedTitle.setText("Edit your note");
            deleteMedNote.setVisibility(View.VISIBLE);
        }

        // This button triggers a method used to save notes to Firestore
        saveMedNote.setOnClickListener(v -> saveMedicalNote());

        // This button triggers a method used to delete a note from Firestore
        deleteMedNote.setOnClickListener(v -> deleteMedNoteFromFireStore());
    }

    void saveMedicalNote(){
        // The note title and content is acquired here and used to save a new note
        String medicNoteTitle = editMedTitle.getText().toString();
        String medicNoteContent = editMedContent.getText().toString();

        // If the note's title hasn't been filled in yet, this message will be triggered
        if(medicNoteTitle == null || medicNoteTitle.isEmpty()){
            editMedTitle.setError("Title is needed to save the note");
            return;
        }

        // The instantiable class is called in a  new object that will contain the String variables declared up above
        MedicInfo medicNotes = new MedicInfo();
        medicNotes.setTitle(medicNoteTitle);
        medicNotes.setContent(medicNoteContent);

        // The object will also automatically trigger another method to save the note to the database
        saveMedNoteToFireStore(medicNotes);
    }

    void saveMedNoteToFireStore(MedicInfo medicNotes){
        // An object of the FireStore database used for saving notes is declared here
        DocumentReference documentReference;

        // If the user is editing a note, the boolean flag will lead to the utility file finding the edited note via an ID
        if(inEditMode){
            documentReference = MedicBox.getCollectionRefForMedicalNotes().document(medDocId);
        }else{
            // Otherwise, if it's a new note, then the same will trigger but a new ID is generated if a note is saved
            documentReference = MedicBox.getCollectionRefForMedicalNotes().document();
        }

        // Then this will trigger an if else method for saving a newly created note in FireStore
        documentReference.set(medicNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // If all goes according to plan, then the note will be saved to the FireStore database
                    MedicBox.showToast(MedicDetails.this, "Medical note has been added");
                    finish();
                }
                else{
                    // Or if an error happens mid-save, this message will appears
                    MedicBox.showToast(MedicDetails.this, "An error occurred!");
                }
            }
        });
    }

    void deleteMedNoteFromFireStore(){
        // The note title and content is acquired here and used to remove a new note
        DocumentReference documentReference;
        documentReference = MedicBox.getCollectionRefForMedicalNotes().document(medDocId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // If all goes according to plan, then the note will be removed to the FireStore database
                    MedicBox.showToast(MedicDetails.this, "Medical note has been deleted");
                    finish();
                }
                else{
                    // Otherwise, if an error happens mid-removal, this message will appear
                    MedicBox.showToast(MedicDetails.this, "An error occurred!");
                }
            }
        });
    }
}