package com.ayush26.event_management_admin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ayush26.event_management_admin.Activities.AdminHome;
import com.ayush26.event_management_admin.Activities.AdminRegister;
import com.ayush26.event_management_admin.Activities.RegistrationActivity;
import com.ayush26.event_management_admin.Activities.Splash;
import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.Models.APIModels.LoginAPICall;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.FragmentLoginBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    FragmentLoginBinding loginBinding;
    TextView registerTV;
    TextInputEditText emailET,passwordET;
    MaterialButton loginButton;
    View view;
    String emailSP="";
    String passwordSP ="";
    SharedPreferences sharedPreferences;
    LoadToast loadToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        loginBinding = FragmentLoginBinding.inflate(getLayoutInflater());
        attachID();

        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        emailSP = sharedPreferences.getString("email","");
        passwordSP = sharedPreferences.getString("password","");
        Log.i("TAG", "onCreateView: SP email + password"+ emailSP+passwordSP);

        if (!emailSP.isEmpty() && !passwordSP.isEmpty())
        {
            emailET.setText(emailSP);
            passwordET.setText(passwordSP);
            loadToast = new LoadToast(view.getContext());
            loadToast.setText("Logging in");
            loadToast.setTranslationY(100);
            loadToast.setBorderColor(R.color.color_accent);
            loadToast.show();
            LoginAdmin(emailSP,passwordSP);
        }

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: clicked on Register ");
                view.getContext().startActivity(new Intent(view.getContext(), AdminRegister.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Logging in");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();



                final String email = emailET.getText().toString();
                final String password = passwordET.getText().toString();
                if(email.isEmpty()){
                    emailET.setError("Please enter an emailID");
                    emailET.requestFocus();
                }if(password.isEmpty()){
                    passwordET.setError("Please enter a password");
                    passwordET.requestFocus();
                }
                if(emailET.getError()==null && passwordET.getError()==null)
                {
                    LoginAdmin(email,password);
                }

            }
        });
        return view;
    }

    void attachID()
    {
        emailET = view.findViewById(R.id.email_field);
        passwordET = view.findViewById(R.id.password_field);
        registerTV = view.findViewById(R.id.login_to_register);
        loginButton = view.findViewById(R.id.login);
    }


    private void LoginAdmin(final String email, final String password) {
        Call<LoginAPICall> call = RetrofitClient.getClient().LoginAdmin(email,password);
        call.enqueue(new Callback<LoginAPICall>() {
            @Override
            public void onResponse(Call<LoginAPICall> call, Response<LoginAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if (response.body()!=null)
                {
                    if (response.body().getSuccess()){
//                        Toast.makeText(view.getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            loadToast.success();
                        emailET.setText("");
                        passwordET.setText("");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token",response.body().getToken());
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.commit();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getContext(), AdminHome.class));
                            }
                        },1000);

                    }
                    else
                        loadToast.error();
//                        Toast.makeText(getContext(),"Login unsuccessful. Please try again",Toast.LENGTH_LONG).show();
                }
                else
                    loadToast.error();
//                    Toast.makeText(getContext(),"Login unsuccessful. Please try again",Toast.LENGTH_LONG).show();



            }

            @Override
            public void onFailure(Call<LoginAPICall> call, Throwable t) {
                Toast.makeText(getContext(),"Could not login",Toast.LENGTH_LONG).show();
            }
        });
    }
}