package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
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
public class AddContact extends AppCompatActivity {

    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    ArrayList<ContactItem> contactItemArrayList;
    ContactRecyclerAdapter adapter;

    Button addCon, homeBtn, emailBtn, smsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }
}