package com.eoghanf.medsupapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.medsupapp.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/*
 *  Class name: SpeakAudio.java
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
 * @reference:  https://www.youtube.com/watch?v=_SE7Sx4Cgqo/SpeakAudio.java
 *
 */

public class SpeechToText extends AppCompatActivity {

    // This request access code is used to help ensure that the Speech to Text Recogniser functionality will work as intended
    public static final int RecordRequestedAudioCode = 1;

    // The buttons and the text field is declared and initialised here from the XML file
    EditText textBox;
    ImageView microphoneBtn;

    Button copyTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        textBox = findViewById(R.id.textArea);
        microphoneBtn = findViewById(R.id.micView);
        copyTextBtn = findViewById(R.id.copyBtn);

        // This is where the recogniser function will work
        microphoneBtn.setOnClickListener(v -> {

            // An Intent object will be used within a constant that helps with recognising text from recorded audio
            Intent in = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            // Here, a designated speech model is used to help fine tune the audio results
            in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // Then, the found text will be set to English
            in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());

            // This shows the user when they should speak into the microphone
            in.putExtra(RecognizerIntent.EXTRA_PROMPT, "Read out your medication/prescription instructions");

           // A try catch method is used for finding the text in the recorded audio and displaying them to the user
            try{
                startActivityForResult(in, RecordRequestedAudioCode);
            }
            // If an error occurs this comment will be shown
            catch(Exception exe){
                Toast.makeText(this, "Exception -> "+exe.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }

        });


        /*
         *
         * @reference:  https://www.youtube.com/watch?v=ctywlOxBCX4/SpeakAudio.java
         *
         */
        copyTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text", textBox.getText().toString());

                clipBoard.setPrimaryClip(clip);

                Toast.makeText(SpeechToText.this, "Copied text to the clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the request code matches up with the AudioRequest code, then this will occur
        if(requestCode == RecordRequestedAudioCode){
            if(resultCode == RESULT_OK && data!= null){
                // Here, an ArrayList object will be used to locate the text found in the audio and puts them into the text box which can then be copied and edited to whatever the user wants it to be.
                ArrayList<String> resultData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                textBox.setText(Objects.requireNonNull(resultData).get(0));
            }
        }
    }
}