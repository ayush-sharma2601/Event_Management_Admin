package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding splashBinding;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());

        handler = new Handler();

        AlphaAnimation logoAnimation = new AlphaAnimation(0.0f,1.0f);
        AlphaAnimation textAnimation = new AlphaAnimation(0.0f,1.0f);
        AlphaAnimation artAnimation = new AlphaAnimation(0.0f,1.0f);

        logoAnimation.setDuration(500);
        textAnimation.setDuration(500);
        artAnimation.setDuration(1500);

        splashBinding.splashLogo.startAnimation(logoAnimation);
        splashBinding.splashTitle.startAnimation(textAnimation);
        splashBinding.splashArtist.startAnimation(artAnimation);
        splashBinding.splashPlantTree.startAnimation(artAnimation);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}