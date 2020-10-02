package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityAdminRegisterBinding;

public class AdminRegister extends AppCompatActivity {

    ActivityAdminRegisterBinding adminRegisterBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminRegisterBinding = ActivityAdminRegisterBinding.inflate(getLayoutInflater());
        setContentView(adminRegisterBinding.getRoot());

        adminRegisterBinding.registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminRegister.this,RegistrationActivity.class));
                finish();
            }
        });
    }
}