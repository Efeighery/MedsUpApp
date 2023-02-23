package com.example.medsupapp;

/*
 *  Class name: MedicationRecyclerAdapter.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 12/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/MedicationRecyclerAdapter.java
 *
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*
 *  Class name: MedicationRecyclerAdapter.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 12/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/MedicationRecyclerAdapter.java
 *
 */

public class MedicationRecyclerAdapter extends RecyclerView.Adapter<MedicationRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<MedicationItem> medicationItemArrayList;
    DatabaseReference databaseReference;

    public MedicationRecyclerAdapter(Context context, ArrayList<MedicationItem> medicationItemArrayList) {
        this.context = context;
        this.medicationItemArrayList = medicationItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MedicationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.medication_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationRecyclerAdapter.ViewHolder holder, int position) {

        MedicationItem medicationItem = medicationItemArrayList.get(position);

        holder.medName.setText("Name: "+medicationItem.getName());
        holder.medRules.setText("Instructions: "+medicationItem.getRules());

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogEditMed viewDialogEditMed = new ViewDialogEditMed();
                viewDialogEditMed.showDialog(context, medicationItem.getmID(), medicationItem.getName(), medicationItem.getRules());
            }
        });

        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDeleteMedDialog viewDeleteMedDialog = new ViewDeleteMedDialog();
                viewDeleteMedDialog.showDialog(context, medicationItem.getmID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicationItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView medName, medRules;

        Button delBtn, updateBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            medName = itemView.findViewById(R.id.nameBox);
            medRules = itemView.findViewById(R.id.rulesBox);

            updateBtn = itemView.findViewById(R.id.updateBtn);
            delBtn = itemView.findViewById(R.id.removeBtn);
        }
    }

    public class ViewDialogEditMed{
        public void showDialog(Context context, String mID, String name, String rules){
            final Dialog dialogue = new Dialog(context);

            dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogue.setCancelable(false);
            dialogue.setContentView(R.layout.alert_dialog_add_new_medication);

            EditText medName = dialogue.findViewById(R.id.medName);
            EditText medRules = dialogue.findViewById(R.id.medRules);

            medName.setText(name);
            medRules.setText(rules);

            Button updateEntry = dialogue.findViewById(R.id.addMed);
            Button cancelEntry = dialogue.findViewById(R.id.cancelAction);

            updateEntry.setText("EDIT MEDICATION");

            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogue.dismiss();
                }
            });

            updateEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String alteredName = medName.getText().toString();
                    String alteredRules = medRules.getText().toString();

                    if(name.isEmpty() || rules.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(alteredName.equals(name) && alteredRules.equals(rules)){
                            Toast.makeText(context, "No changes made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseReference.child("MEDICATIONS").child(mID).setValue(new MedicationItem(mID, name, rules));
                            Toast.makeText(context, "Entry updated", Toast.LENGTH_SHORT).show();
                            dialogue.dismiss();
                        }
                    }
                }
            });
            dialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogue.show();
        }
    }

    public class ViewDeleteMedDialog{
        public void showDialog(Context context, String mID){
            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.view_dialog_remove_medication);

            Button removeEntry = dialog.findViewById(R.id.delBtn);
            Button cancelEntry = dialog.findViewById(R.id.cancelBtn);

            removeEntry.setText("REMOVE MEDICATION ENTRY");

            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            removeEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child("MEDICATIONS").child(mID).removeValue();
                    Toast.makeText(context, "Entry removed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }
}
