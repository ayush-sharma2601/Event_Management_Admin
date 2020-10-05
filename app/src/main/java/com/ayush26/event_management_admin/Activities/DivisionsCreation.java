package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityDivisionsCreationBinding;

public class DivisionsCreation extends AppCompatActivity {

    ActivityDivisionsCreationBinding divisionsCreationBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        divisionsCreationBinding = ActivityDivisionsCreationBinding.inflate(getLayoutInflater());
        setContentView(divisionsCreationBinding.getRoot());


    }
}