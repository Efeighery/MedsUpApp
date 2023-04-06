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

public class DiagnosisDetails extends AppCompatActivity {

    EditText editDiagTitle, editDiagContent;

    ImageButton saveDiagNote;

    TextView pageConTitle;

    TextView deleteDiagNote;

    boolean inEditMode = false;

    String title, content, diaDocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        editDiagTitle = findViewById(R.id.diagTitle);
        editDiagContent = findViewById(R.id.diagInfo);
        saveDiagNote = findViewById(R.id.saveNoteButton);
        pageConTitle = findViewById(R.id.pageTitle);
        deleteDiagNote = findViewById(R.id.removeDiagNote);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        diaDocId = getIntent().getStringExtra("diaDocId");

        if(diaDocId != null && !diaDocId.isEmpty()){
            inEditMode = true;
        }

        editDiagTitle.setText(title);
        editDiagContent.setText(content);

        if(inEditMode){
            pageConTitle.setText("Edit your note");
            deleteDiagNote.setVisibility(View.VISIBLE);
        }

        saveDiagNote.setOnClickListener(v -> saveDiagnosisNote());
        deleteDiagNote.setOnClickListener(v -> deleteDiagNoteFromFireStore());
    }

    private void deleteDiagNoteFromFireStore() {
        DocumentReference documentReference;
        documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document(diaDocId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    DiagBox.showToast(DiagnosisDetails.this, "Diagnosis note has been deleted");
                    finish();
                }
                else{
                    DiagBox.showToast(DiagnosisDetails.this, "Error has occurred!");
                }
            }
        });
    }

    void saveDiagnosisNote() {
        String diagnosisNoteTitle = editDiagTitle.getText().toString();
        String diagnosisNoteContent = editDiagContent.getText().toString();

        if(diagnosisNoteTitle == null || diagnosisNoteTitle.isEmpty()){
            editDiagTitle.setError("Title is needed to save the note");
            return;
        }

        DiagnosisInfo diagNotes = new DiagnosisInfo();
        diagNotes.setTitle(diagnosisNoteTitle);
        diagNotes.setContent(diagnosisNoteContent);

        saveDiagnosisNoteToFireStore(diagNotes);
    }

    void saveDiagnosisNoteToFireStore(DiagnosisInfo diagNotes) {
        DocumentReference documentReference;

        if (inEditMode) {
            documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document(diaDocId);
        } else {
            documentReference = DiagBox.getCollectionRefForDiagnosisNotes().document();
        }
        documentReference.set(diagNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ContactBox.showToast(DiagnosisDetails.this, "Contact note has been added");
                    finish();
                } else {
                    ContactBox.showToast(DiagnosisDetails.this, "An error occurred");

                }
            }
        });
    }
}