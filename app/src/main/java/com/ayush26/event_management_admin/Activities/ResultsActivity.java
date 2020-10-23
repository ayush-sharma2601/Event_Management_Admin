package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityResultsBinding;

public class ResultsActivity extends AppCompatActivity {

    ActivityResultsBinding resultsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultsBinding = ActivityResultsBinding.inflate(getLayoutInflater());
        setContentView(resultsBinding.getRoot());

    }
}