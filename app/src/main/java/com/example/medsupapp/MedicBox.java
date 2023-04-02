package com.example.medsupapp;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/*
 *  Class name: MedicBox.java
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
 * @reference: https://www.youtube.com/watch?v=jzVmjU2PFbg&lc=UgzLOyUfXTI67vUWmAN4AaABAg.9neYhJvabtS9nyWTcGnWoh/MedicBox.java
 *
 */

public class MedicBox {

    static void showToast(Context context, String reminder){
        Toast.makeText(context, reminder, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionRefForMedicalNotes(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Medications").document(firebaseUser.getUid()).collection("MyMedicationNotes");
    }


}
