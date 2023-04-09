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
 *  Class name: DiagnosisDetails.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/DiagnosisDetails.java
 *
 */

public class DiagnosisDetails extends AppCompatActivity {

    // The XML variables are declared here
    EditText editDiagTitle, editDiagContent;
    ImageButton saveDiagNote;
    TextView pageConTitle;
    TextView deleteDiagNote;

    // A Boolean flag that helps to act as an indicator for when a user is editing a saved note
    boolean inEditMode = false;

    // Used to get the String variables for a note and to track down the correct notes specified by a user
    String title, content, diaDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        // The XML variables are initialised by their IDs
        editDiagTitle = findViewById(R.id.diagTitle);
        editDiagContent = findViewById(R.id.diagInfo);
        saveDiagNote = findViewById(R.id.saveNoteButton);
        pageConTitle = findViewById(R.id.pageTitle);
        deleteDiagNote = findViewById(R.id.removeDiagNote);

        // This will grab the notes saved by a user
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        diaDocId = getIntent().getStringExtra("diaDocId");

        // If a note ID isn't empty, then the Boolean flag will be set as true (The user is currently in Editing Note Mode)
        if(diaDocId != null && !diaDocId.isEmpty()){
            inEditMode = true;
        }

        // The Note Variables are set here
        editDiagTitle.setText(title);
        editDiagContent.setText(content);

        // If the user is currently editing a note, then the delete button will be made visible
        if(inEditMode){
            pageConTitle.setText("Edit your note");
            deleteDiagNote.setVisibility(View.VISIBLE);
        }

        // This button triggers a method used to save notes to Firestore
        saveDiagNote.setOnClickListener(v -> saveDiagnosisNote());
        // This button triggers a method used to delete a note from Firestore
        deleteDiagNote.setOnClickListener(v -> deleteDiagNoteFromFireStore());
    }

    // This is used to help remove a specified note from the database
    private void deleteDiagNoteFromFireStore() {
        // The note title and content is acquired here and used to delete a new note
        DocumentReference documentReference;

        // Once again, the FireStore database object is created and the ID object is used to find a requested note
        documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document(diaDocId);


        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // If all goes according to plan, then the note will be removed to the FireStore database
                    DiagBox.showToast(DiagnosisDetails.this, "Diagnosis note has been deleted");
                    finish();
                }
                // Otherwise, if an error happens mid-removal, this message will appear
                else{
                    DiagBox.showToast(DiagnosisDetails.this, "Error has occurred!");
                }
            }
        });
    }

    // This is used to help save a specified note from the database
    void saveDiagnosisNote() {
        // Once again, the FireStore database object is created and the ID object is used to find a requested note
        String diagnosisNoteTitle = editDiagTitle.getText().toString();
        String diagnosisNoteContent = editDiagContent.getText().toString();

        // This if else statement will remove the note
        if(diagnosisNoteTitle == null || diagnosisNoteTitle.isEmpty()){
            editDiagTitle.setError("Title is needed to save the note");
            return;
        }

        // The instantiable class is called in a  new object that will contain the String variables declared up above
        DiagnosisInfo diagNotes = new DiagnosisInfo();
        diagNotes.setTitle(diagnosisNoteTitle);
        diagNotes.setContent(diagnosisNoteContent);

        // The object will also automatically trigger another method to save the note to the database
        saveDiagnosisNoteToFireStore(diagNotes);
    }

    void saveDiagnosisNoteToFireStore(DiagnosisInfo diagNotes) {
        // An object of the FireStore database used for saving notes is declared here
        DocumentReference documentReference;

        // If the user is editing a note, the boolean flag will lead to the utility file finding the edited note via an ID
        if (inEditMode) {
            documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document(diaDocId);
        } else {
            // Otherwise, if it's a new note, then the same will trigger but a new ID is generated if a note is saved
            documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document();
        }

        // Then this will trigger an if else method for saving a newly created note in FireStore
        documentReference.set(diagNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // If all goes according to plan, then the note will be saved to the FireStore database
                    ContactBox.showToast(DiagnosisDetails.this, "Contact note has been added");
                    finish();
                } else {
                    // Or if an error happens mid-save, this message will appears
                    ContactBox.showToast(DiagnosisDetails.this, "An error occurred");
                }
            }
        });
    }
}