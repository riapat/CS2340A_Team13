package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cs2340a_team13.R;
import android.os.Handler;
import android.content.Intent;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 4000; //time in milliseconds of wait
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //handler delays the transition to the login screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {     //runs when timer is over
                Intent i = new Intent(SplashScreen.this, LoginScreen.class);
                startActivity(i);
                finish();           //close activity
            }
        }, SPLASH_TIME_OUT);
    }
}