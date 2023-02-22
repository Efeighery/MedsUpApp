package com.example.medsupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

/*
 *  Class name: SplasherScreen.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 12/01/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference:  https://www.geeksforgeeks.org/splash-screen-in-android/SplasherScreen.java
 *
 */

public class SplasherScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splasher_screen);

        // Here the window will be brought up to fill the entire screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // And a handler is called in to run the task for a certain time frame
        // This is done with the Runnable schematic that's also used for Threads
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // An Intent object is made where the splash screen will load into the registration screen
                Intent haste = new Intent(SplasherScreen.this, Register.class);

                // The Intent object is then activated here
                startActivity(haste);

                // While the finish method will stop the current activity
                finish();
            }
            // Here the delay value is set to run for a specified time, here it's set to 3500 milliseconds meaning it will load for 3.5 seconds
        }, 3500);
    }
}