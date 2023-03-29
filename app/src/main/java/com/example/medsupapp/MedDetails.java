package com.example.medsupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class MedDetails extends AppCompatActivity {

    EditText editMedTitle, editMedContent;
    ImageButton saveMedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_details);

        editMedTitle = findViewById(R.id.medTitle);
        editMedContent = findViewById(R.id.medInfo);
        saveMedNote = findViewById(R.id.saveNoteButton);

        saveMedNote.setOnClickListener(v -> saveMedicalNote());
    }

    void saveMedicalNote(){
        String medicNoteTitle = editMedTitle.getText().toString();
        String medicNoteContent = editMedContent.getText().toString();

        if(medicNoteTitle == null || medicNoteTitle.isEmpty()){
            editMedTitle.setError("Title is needed to save the note");
            return;
        }

        MedicNotes medicNotes = new MedicNotes();
        medicNotes.setTitle(medicNoteTitle);
        medicNotes.setContent(medicNoteContent);

        saveMedNoteToFireStore(medicNotes);
    }

    void saveMedNoteToFireStore(MedicNotes medicNotes){
        DocumentReference documentReference;
        documentReference = Box.getCollectionRefForMedicalNotes().document();

        documentReference.set(medicNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // Note will be added
                    Box.showToast(MedDetails.this, "Medical note has been added");
                    finish();
                }
                else{
                    Box.showToast(MedDetails.this, "An error occurred!");
                }
            }
        });
    }
}