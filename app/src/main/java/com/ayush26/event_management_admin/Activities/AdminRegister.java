package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ayush26.event_management_admin.Fragments.LoginFragment;
import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityAdminRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminRegister extends AppCompatActivity {

    ActivityAdminRegisterBinding adminRegisterBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminRegisterBinding = ActivityAdminRegisterBinding.inflate(getLayoutInflater());
        setContentView(adminRegisterBinding.getRoot());

        adminRegisterBinding.adminReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = adminRegisterBinding.nameField.getText().toString();
                final String email = adminRegisterBinding.emailField.getText().toString();
                final String adminKey = adminRegisterBinding.adminKeyField.getText().toString();
                final String password = adminRegisterBinding.passwordField.getText().toString();
                final String confirmPass = adminRegisterBinding.confirmPasswordField.getText().toString();

                if(name.isEmpty()){
                    adminRegisterBinding.nameField.setError("Please enter a name");
                    adminRegisterBinding.nameField.requestFocus();
                }
                if(email.isEmpty()){
                    adminRegisterBinding.emailField.setError("Please enter an emailID");
                    adminRegisterBinding.emailField.requestFocus();
                }if(password.isEmpty()){
                    adminRegisterBinding.passwordField.setError("Please enter a password");
                    adminRegisterBinding.passwordField.requestFocus();
                }if(confirmPass.isEmpty()){
                    adminRegisterBinding.confirmPasswordField.setError("Please confirm your password");
                    adminRegisterBinding.confirmPasswordField.requestFocus();
                }if(adminKey.isEmpty()){
                    adminRegisterBinding.adminKeyField.setError("Please enter your admin key");
                    adminRegisterBinding.adminKeyField.requestFocus();
                }
                if(!password.equals(confirmPass)){
                    adminRegisterBinding.confirmPasswordField.setError("Password doesnt match.Try again");
                    adminRegisterBinding.confirmPasswordField.requestFocus();
                }
                if(adminRegisterBinding.nameField.getError()==null && adminRegisterBinding.emailField.getError()==null && adminRegisterBinding.passwordField.getError()==null && adminRegisterBinding.confirmPasswordField.getError()==null && adminRegisterBinding.adminKeyField.getError()==null )
                {
                    RegisterAdmin(name,email,adminKey,password);
                }
            }
        });

        adminRegisterBinding.registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminRegister.this,RegistrationActivity.class));
                finish();
            }
        });
    }

    private void RegisterAdmin(String name, String email, String adminKey, String password) {

        Call<APICall> call = RetrofitClient.getClient().SignupAdmin(name,email,password,adminKey);
        call.enqueue(new Callback<APICall>() {
            @Override
            public void onResponse(Call<APICall> call, Response<APICall> response) {
//                Toast.makeText(AdminRegister.this,response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        Snackbar.make(adminRegisterBinding.getRoot(),"Registration Successful",Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminRegister.this, RegistrationActivity.class));
                    }
                    else
                        Toast.makeText(AdminRegister.this,"unsuccessful",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AdminRegister.this,"unsuccessful",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<APICall> call, Throwable t) {
                Toast.makeText(AdminRegister.this,"Could not register",Toast.LENGTH_LONG).show();
            }
        });
    }
}