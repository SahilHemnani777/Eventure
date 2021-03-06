package com.teamsar.eventure.activities.activity_splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.teamsar.eventure.activities.activity_home.HomeActivity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // NOTE: we are not setting any content view for SplashScreenActivity
        // goto Home Activity after cold boot
        Intent intentToHomeScreen=new Intent(SplashScreen.this, HomeActivity.class);
        startActivity(intentToHomeScreen);
        // finish this activity
        finish();
    }
}