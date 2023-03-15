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

    // A Context object is needed for the removal and editing of a diagnosis entry
    Context context;

    // The instantiable class is declared as an ArrayList
    ArrayList<DiagnosisItem> diagnosisItemArrayList;

    // The Database Reference will be used to call in the Realtime Database table for various operations
    DatabaseReference databaseReference;

    public DiagnosisRecyclerAdapter(Context context, ArrayList<DiagnosisItem> diagnosisItemArrayList) {
        this.context = context;
        this.diagnosisItemArrayList = diagnosisItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public DiagnosisRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // The XML file will be set to the LayoutInflater object and later encrypted into the View object
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.health_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisRecyclerAdapter.ViewHolder holder, int position) {
        // Here, the instantiable class (DiagnosisItem) is declared into the ArrayList object while this method updates the ViewHolder content
        DiagnosisItem diagnosisItem = diagnosisItemArrayList.get(position);

        // The holder will be where the diagnosis information is updated
        holder.healthTerm.setText("Diagnosis: "+diagnosisItem.getTerm());

        // In these two button methods, the ViewDialog aspects will take the following contexts and Diagnosis variables to make the methods work properly
        holder.updateTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDiaTermUpdate viewDiaTermUpdate = new ViewDiaTermUpdate();
                viewDiaTermUpdate.showDialog(context, diagnosisItem.gethID(), diagnosisItem.getTerm());
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

    // The Item count is set to the size of the ArrayList
    @Override
    public int getItemCount() {
        return diagnosisItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // The TextViews and buttons for the diagnosis field and the aforementioned buttons are declared and initialised here
        TextView healthTerm;

        Button removeTerm, updateTerm;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            healthTerm = itemView.findViewById(R.id.termBox);
            removeTerm = itemView.findViewById(R.id.removeBtn);
            updateTerm = itemView.findViewById(R.id.updateBtn);
        }
    }

    public class ViewDiaTermUpdate{
        // This method will be used for when the user wants to change a saved diagnosis
        public void showDialog(Context context, String hID, String term){
            // Like in the Contact file, a dialogue object will be set to the required functionality (in this case, updating an existing diagnosis)
            final Dialog d = new Dialog(context);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setCancelable(false);
            d.setContentView(R.layout.alert_dialog_add_new_health);

            // The variables are set and declared here along with the buttons
            EditText diagnosisName = d.findViewById(R.id.healthName);
            Button updateDiag = d.findViewById(R.id.addHeal);
            Button exitBtn = d.findViewById(R.id.cancelAction);

            diagnosisName.setText(term);

            updateDiag.setText("ADD DIAGNOSIS");

            // If a user wants to leave, then dialogue window will be dismissed
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

            // If a user wants to update a saved diagnosis, this method will be activated
            updateDiag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This will get the variables to allow for edits to be made
                    String alteredTerm = diagnosisName.getText().toString();

                    if(alteredTerm.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Or if any edits weren't made, this message will show
                        if(alteredTerm.equals(term)){
                            Toast.makeText(context, "No edits made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // Otherwise, any changes made will be made to the database entry
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
            // Like in the Contact file, a dialogue object will be set to the required functionality (in this case, updating an existing contact)
            dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogs.setCancelable(false);

            // This is used to activate the delete query when the delete button is clicked
            dialogs.setContentView(R.layout.view_dialog_remove_health);

            // Unlike the update method, only the buttons are initialised here
            Button removeTerm = dialogs.findViewById(R.id.delBtn);
            Button leaveBtn = dialogs.findViewById(R.id.cancelBtn);

            // If the users wants to exit, then the dialogue will be dismissed
            leaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogs.dismiss();
                }
            });

            // Here, the method will find the id for a saved diagnosis to remove the diagnosis entry, which is then notified to the user as seen here
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
