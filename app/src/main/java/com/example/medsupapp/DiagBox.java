package com.example.medsupapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
/*
 *  Class name: DiagBox.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/DiagBox.java
 *
 */
public class DiagBox {

    static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionRefForDiagnosisNotes(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Diagnoses").document(firebaseUser.getUid()).collection("MyDiagnosisNotes");
    }
}
