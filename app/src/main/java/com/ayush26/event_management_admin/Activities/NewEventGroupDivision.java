package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityNewEventGroupDivisionBinding;

public class NewEventGroupDivision extends AppCompatActivity {

    ActivityNewEventGroupDivisionBinding groupDivisionBinding;
    Button createEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupDivisionBinding = ActivityNewEventGroupDivisionBinding.inflate(getLayoutInflater());
        setContentView(groupDivisionBinding.getRoot());

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewEventGroupDivision.this,AdminHome.class));
                finish();
            }
        });
        attachID();
    }

    private void attachID() {
        createEventBtn = findViewById(R.id.create_event_btn);
    }
}