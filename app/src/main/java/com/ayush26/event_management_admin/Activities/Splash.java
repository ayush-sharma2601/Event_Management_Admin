package com.ayush26.event_management_admin.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RemoteConfigClass;
import com.ayush26.event_management_admin.databinding.ActivitySplashBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding splashBinding;
    Handler handler;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("base_url");
    RemoteConfigClass remoteConfigClass = RemoteConfigClass.getRemoteConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                remoteConfigClass.setBaseURL(snapshot.getValue(String.class));
                final SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("base_url",snapshot.getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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