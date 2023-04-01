package com.example.medsupapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MedicBox {

    static void showToast(Context context, String reminder){
        Toast.makeText(context, reminder, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionRefForMedicalNotes(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Medications").document(firebaseUser.getUid()).collection("MyMedicationNotes");
    }


}
