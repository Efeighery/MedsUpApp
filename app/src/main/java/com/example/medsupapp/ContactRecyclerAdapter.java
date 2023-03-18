package com.example.medsupapp;

/*
 *  Class name: ContactRecyclerAdapter.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 14/12/2022
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/ContactRecyclerAdapter.java
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

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder> {

    // A Context object is needed for the removal and editing of a contact entry
    Context context;

    // The instantiable class is declared as an ArrayList
    ArrayList<ContactItem> contactItemArrayList;

    // The Database Reference will be used to call in the Realtime Database table for various operations
    DatabaseReference databaseReference;

    public ContactRecyclerAdapter(Context context, ArrayList<ContactItem> contactItemArrayList) {
        this.context = context;
        this.contactItemArrayList = contactItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        // The XML file will be set to the LayoutInflater object and later encrypted into the View object
        View view = layoutInflater.inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerAdapter.ViewHolder holder, int position) {
        // Here, the instantiable class (ContactItem) is declared into the ArrayList object while this method updates the ViewHolder content
        ContactItem contactItem = contactItemArrayList.get(position);

        // The holder will be where the contact information is updated
        holder.conName.setText("Name: "+contactItem.getName());
        holder.conEmailAd.setText("Name: "+contactItem.getEmail());
        holder.conPhone.setText("Name: "+contactItem.getPhone());

        // In these two button methods, the ViewDialog aspects will take the following contexts and Contacts variables to make the methods work properly

        /*
        holder.editCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDiaUpdateContact viewDiaUpdateContact = new ViewDiaUpdateContact();
                viewDiaUpdateContact.showDialog(context, contactItem.getcID(), contactItem.getName(), contactItem.getEmail(), contactItem.getPhone());
            }
        });
        */
        holder.delCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogContactRemoval viewDialogContactRemoval = new ViewDialogContactRemoval();
                viewDialogContactRemoval.showDialog(context, contactItem.getcID());
            }
        });

    }

    /*
    public class ViewDiaUpdateContact{
        // This method will be used for when the user wants to change a saved contact
        public void showDialog(Context context, String cID, String name, String email, String phone){

            // Like in the Contact file, a dialogue object will be set to the required functionality (in this case, updating an existing contact)
            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_contact);

            // The variables are set and declared here along with the buttons
            EditText conName = dialog.findViewById(R.id.conName);
            EditText conEmailAd = dialog.findViewById(R.id.conEmailAdd);
            EditText conPhone = dialog.findViewById(R.id.conTelephone);

            conName.setText(name);
            conEmailAd.setText(email);
            conPhone.setText(phone);

            Button updateContact = dialog.findViewById(R.id.addCon);
            Button exitField = dialog.findViewById(R.id.cancelAction);

            updateContact.setText("Edit contact");

            // If a user wants to leave, then dialogue window will be dismissed
            exitField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // If a user wants to update a saved contact, this method will be activated
            updateContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // This will get the variables to allow for edits to be made
                    String alteredName = conName.getText().toString();
                    String alteredEmail = conEmailAd.getText().toString();
                    String alteredPhone = conPhone.getText().toString();

                    if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                        Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        // Or if any edits weren't made, this message will show
                        if(alteredName.equals(name) && alteredEmail.equals(email) && alteredPhone.equals(phone)){
                            Toast.makeText(context, "Hmm, no edits made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // Otherwise, any changes made will be made to the database entry
                            databaseReference.child("CONTACTS").child(cID).setValue(new ContactItem(cID, name, email, phone));
                            Toast.makeText(context, "And the user is updated now", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

     */

    public class ViewDialogContactRemoval{
        public void showDialog(Context context, String cID){
            final Dialog dialog = new Dialog(context);

            // Like in the Contact file, a dialogue object will be set to the required functionality (in this case, updating an existing contact)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);

            // This is used to activate the delete query when the delete button is clicked
            dialog.setContentView(R.layout.view_dialog_remove_contact);

            // Unlike the update method, only the buttons are initialised here
            Button removeCon = dialog.findViewById(R.id.delBtn);
            Button exitBtn = dialog.findViewById(R.id.cancelBtn);

            // If the users wants to exit, then the dialogue will be dismissed
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            // Here, the method will find the id for a saved contact to remove the contact entry, which is then notified to the user as seen here
            removeCon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child("CONTACTS").child(cID).removeValue();
                    Toast.makeText(context, "Entry removed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    // The Item count is set to the size of the ArrayList
    @Override
    public int getItemCount() {
        return contactItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        // The TextViews and buttons for the contact fields and the aforementioned buttons are declared and initialised here
        TextView conName, conPhone, conEmailAd;

        Button delCon, editCon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            conName = itemView.findViewById(R.id.nameCon);
            conPhone = itemView.findViewById(R.id.phoneCon);
            conEmailAd = itemView.findViewById(R.id.emailCon);

            delCon = itemView.findViewById(R.id.removeBtn);
            editCon = itemView.findViewById(R.id.updateBtn);

        }
    }
}
