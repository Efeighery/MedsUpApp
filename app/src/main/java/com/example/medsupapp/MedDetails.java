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
 *  Class name: MedDetails.java
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

public class MedDetails extends AppCompatActivity {

    EditText editMedTitle, editMedContent;
    ImageButton saveMedNote;
    TextView pageMedTitle;
    TextView deleteMedNote;

    boolean inEditMode = false;

    String title, content, medDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_details);

        editMedTitle = findViewById(R.id.medTitle);
        editMedContent = findViewById(R.id.medInfo);
        saveMedNote = findViewById(R.id.saveNoteButton);
        pageMedTitle = findViewById(R.id.pageTitle);
        deleteMedNote = findViewById(R.id.removeMedNote);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        medDocId = getIntent().getStringExtra("medDocId");

        if(medDocId!= null && !medDocId.isEmpty()){
            inEditMode = true;
        }

        editMedTitle.setText(title);
        editMedContent.setText(content);

        if(inEditMode){
            pageMedTitle.setText("Edit your note");
            deleteMedNote.setVisibility(View.VISIBLE);
        }

        saveMedNote.setOnClickListener(v -> saveMedicalNote());

        deleteMedNote.setOnClickListener(v -> deleteMedNoteFromFireStore());
    }

    void saveMedicalNote(){
        String medicNoteTitle = editMedTitle.getText().toString();
        String medicNoteContent = editMedContent.getText().toString();

        if(medicNoteTitle == null || medicNoteTitle.isEmpty()){
            editMedTitle.setError("Title is needed to save the note");
            return;
        }

        MedicInfo medicNotes = new MedicInfo();
        medicNotes.setTitle(medicNoteTitle);
        medicNotes.setContent(medicNoteContent);

        saveMedNoteToFireStore(medicNotes);
    }

    void saveMedNoteToFireStore(MedicInfo medicNotes){
        DocumentReference documentReference;

        if(inEditMode){
            // For the situation where a note is updated
            documentReference = MedicBox.getCollectionRefForMedicalNotes().document(medDocId);
        }else{
            // For when a user creates a note
            documentReference = MedicBox.getCollectionRefForMedicalNotes().document();
        }

        documentReference.set(medicNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Note will be added
                    MedicBox.showToast(MedDetails.this, "Medical note has been added");
                    finish();
                }
                else{
                    MedicBox.showToast(MedDetails.this, "An error occurred!");
                }
            }
        });
    }

    void deleteMedNoteFromFireStore(){
        DocumentReference documentReference;
        documentReference = MedicBox.getCollectionRefForMedicalNotes().document(medDocId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Note will be removed
                    MedicBox.showToast(MedDetails.this, "Medical note has been deleted");
                    finish();
                }
                else{
                    MedicBox.showToast(MedDetails.this, "An error occurred!");
                }
            }
        });
    }
}