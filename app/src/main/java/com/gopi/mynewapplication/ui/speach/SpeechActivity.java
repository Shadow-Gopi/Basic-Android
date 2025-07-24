package com.gopi.mynewapplication.ui.speach;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.gopi.mynewapplication.R;

public class SpeechActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main, new SpeechFragment())
                    .commit();
        }

    }
}