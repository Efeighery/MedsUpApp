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

    // A Context object is needed for the removal and editing of a medication entry
    Context context;

    // The instantiable class is declared as an ArrayList
    ArrayList<MedicationItem> medicationItemArrayList;

    // The Database Reference will be used to call in the Realtime Database table for various operations
    DatabaseReference databaseReference;

    public MedicationRecyclerAdapter(Context context, ArrayList<MedicationItem> medicationItemArrayList) {
        this.context = context;
        this.medicationItemArrayList = medicationItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public MedicationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // The XML file will be set to the LayoutInflater object and later encrypted into the View object
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.medication_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationRecyclerAdapter.ViewHolder holder, int position) {
        // Here, the instantiable class (MedicationItem) is declared into the ArrayList object while this method updates the ViewHolder content
        MedicationItem medicationItem = medicationItemArrayList.get(position);

        // The holder will be where the medication information is updated
        holder.medName.setText("Name: "+medicationItem.getName());
        holder.medRules.setText("Instructions: "+medicationItem.getRules());

        // In these two button methods, the ViewDialog aspects will take the following contexts and Medication variables to make the methods work properly
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

    // The Item count is set to the size of the ArrayList
    @Override
    public int getItemCount() {
        return medicationItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // The TextViews and buttons for the medication field and the aforementioned buttons are declared and initialised here
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
        // This method will be used for when the user wants to change a saved medication
        public void showDialog(Context context, String mID, String name, String rules){
            // Like in the Medication file, a dialogue object will be set to the required functionality (in this case, updating an existing medication)
            final Dialog dialogue = new Dialog(context);

            dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogue.setCancelable(false);
            dialogue.setContentView(R.layout.alert_dialog_add_new_medication);

            // The variables are set and declared here along with the buttons
            EditText medName = dialogue.findViewById(R.id.medName);
            EditText medRules = dialogue.findViewById(R.id.medRules);

            medName.setText(name);
            medRules.setText(rules);

            Button updateEntry = dialogue.findViewById(R.id.addMed);
            Button cancelEntry = dialogue.findViewById(R.id.cancelAction);

            updateEntry.setText("EDIT MEDICATION");

            // If a user wants to leave, then dialogue window will be dismissed
            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogue.dismiss();
                }
            });

            // If a user wants to update a saved medication, this method will be activated
            updateEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This will get the variables to allow for edits to be made
                    String alteredName = medName.getText().toString();
                    String alteredRules = medRules.getText().toString();

                    if(name.isEmpty() || rules.isEmpty()){
                        Toast.makeText(context, "No blank spaces", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Or if any edits weren't made, this message will show
                        if(alteredName.equals(name) && alteredRules.equals(rules)){
                            Toast.makeText(context, "No changes made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // Otherwise, any changes made will be made to the database entry
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
            // Like in the Contact file, a dialogue object will be set to the required functionality (in this case, updating an existing medication)
            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);

            // This is used to activate the delete query when the delete button is clicked
            dialog.setContentView(R.layout.view_dialog_remove_medication);

            Button removeEntry = dialog.findViewById(R.id.delBtn);
            Button cancelEntry = dialog.findViewById(R.id.cancelBtn);

            // If the users wants to exit, then the dialogue will be dismissed
            removeEntry.setText("REMOVE MEDICATION ENTRY");

            cancelEntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // Here, the method will find the id for a saved medication to remove the medication entry, which is then notified to the user as seen here
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
