package com.example.medsupapp;

/*
 *  Class name: AddContact.java
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
 * @reference:  https://www.youtube.com/watch?v=1Ifc2g6Hv8k&t=2055s/AddContact.java
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

    Context context;

    ArrayList<ContactItem> contactItemArrayList;

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

        View view = layoutInflater.inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerAdapter.ViewHolder holder, int position) {
        ContactItem contactItem = contactItemArrayList.get(position);

        holder.conName.setText("Name: "+contactItem.getName());
        holder.conEmailAd.setText("Name: "+contactItem.getEmail());
        holder.conPhone.setText("Name: "+contactItem.getPhone());

        holder.editCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogContactEdit viewDialogContactEdit = new ViewDialogContactEdit();
                viewDialogContactEdit.showDialog(context, contactItem.getcID(), contactItem.getName(), contactItem.getEmail(), contactItem.getPhone());
            }
        });

        holder.delCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewDialogContactRemoval viewDialogContactRemoval = new ViewDialogContactRemoval();
                viewDialogContactRemoval.showDialog(context, contactItem.getcID());
            }
        });



    }

    public class ViewDialogContactEdit{
        public void showDialog(Context context, String cID, String name, String email, String phone){

            final Dialog dialog = new Dialog(context);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.alert_dialog_add_new_contact);

            EditText conName = dialog.findViewById(R.id.conName);
            EditText conEmailAd = dialog.findViewById(R.id.conEmailAdd);
            EditText conPhone = dialog.findViewById(R.id.conTelephone);

            conName.setText(name);
            conEmailAd.setText(email);
            conPhone.setText(phone);

            Button updateContact = dialog.findViewById(R.id.addCon);
            Button exitField = dialog.findViewById(R.id.cancelAction);

            updateContact.setText("Edit contact");

            exitField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String alteredName = conName.getText().toString();
                    String alteredEmail = conEmailAd.getText().toString();
                    String alteredPhone = conPhone.getText().toString();

                    if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                        Toast.makeText(context, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(alteredName.equals(name) && alteredEmail.equals(email) && alteredPhone.equals(phone)){
                            Toast.makeText(context, "Hmm, no edits made", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            databaseReference.child("contact").child(cID).setValue(new ContactItem(cID, name, email, phone));
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

    public class ViewDialogContactRemoval{
        public void showDialog(Context context, String cID){
            final Dialog dialog = new Dialog(context);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);

            dialog.setContentView(R.layout.view_dialog_remove_contact);

            Button removeCon = dialog.findViewById(R.id.delBtn);
            Button exitBtn = dialog.findViewById(R.id.cancelBtn);

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

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

    @Override
    public int getItemCount() {
        return contactItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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
