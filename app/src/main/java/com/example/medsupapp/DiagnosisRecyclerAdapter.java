package com.example.medsupapp;

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
 *  Class name: HealthRecyclerAdapter.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 14/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/HealthRecyclerAdapter.java
 *
 */

public class DiagnosisRecyclerAdapter extends RecyclerView.Adapter<DiagnosisRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<DiagnosisItem> diagnosisItemArrayList;
    DatabaseReference databaseReference;

    public DiagnosisRecyclerAdapter(Context context, ArrayList<DiagnosisItem> diagnosisItemArrayList) {
        this.context = context;
        this.diagnosisItemArrayList = diagnosisItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public DiagnosisRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.health_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisRecyclerAdapter.ViewHolder holder, int position) {
        DiagnosisItem diagnosisItem = diagnosisItemArrayList.get(position);

        holder.healthTerm.setText("Diagnosis: "+diagnosisItem.getTerm());

        holder.updateTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogEditTerm viewDialogEditTerm = new ViewDialogEditTerm();
                viewDialogEditTerm.showDialog(context, diagnosisItem.gethID(), diagnosisItem.getTerm());
            }
        });

        holder.removeTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogTermDelete viewDialogTermDelete = new ViewDialogTermDelete();
                viewDialogTermDelete.showDialog(context, diagnosisItem.gethID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return diagnosisItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView healthTerm;

        Button removeTerm, updateTerm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            healthTerm = itemView.findViewById(R.id.termBox);
            removeTerm = itemView.findViewById(R.id.removeBtn);
            updateTerm = itemView.findViewById(R.id.updateBtn);
        }
    }

    public class ViewDialogEditTerm{
        public void showDialog(Context context, String hID, String term){
            final Dialog d = new Dialog(context);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setCancelable(false);
            d.setContentView(R.layout.alert_dialog_add_new_health);

            EditText diagnosisName = d.findViewById(R.id.healthName);
            Button updateDiag = d.findViewById(R.id.addHeal);
            Button exitBtn = d.findViewById(R.id.cancelAction);

            diagnosisName.setText(term);

            updateDiag.setText("ADD DIAGNOSIS");

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

            updateDiag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String alteredTerm = diagnosisName.getText().toString();

                    if(alteredTerm.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(alteredTerm.equals(term)){
                            Toast.makeText(context, "No edits made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseReference.child("CONDITIONS").child(hID).setValue(new DiagnosisItem(hID, term));
                            Toast.makeText(context, "Entry edits confirmed!", Toast.LENGTH_SHORT).show();
                            d.dismiss();
                        }
                    }
                }
            });

        }
    }

    public class ViewDialogTermDelete{
        public void showDialog(Context context, String hID){
            final Dialog dialogs = new Dialog(context);
            dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogs.setCancelable(false);
            dialogs.setContentView(R.layout.view_dialog_remove_health);

            Button removeTerm = dialogs.findViewById(R.id.delBtn);
            Button leaveBtn = dialogs.findViewById(R.id.cancelBtn);

            leaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogs.dismiss();
                }
            });

            removeTerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child("CONDITIONS").child(hID).removeValue();
                    Toast.makeText(context, "Entry removal confirmed", Toast.LENGTH_LONG).show();
                    dialogs.dismiss();
                }
            });

            dialogs.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogs.show();
        }
    }
}
