package com.ayush26.event_management_admin.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ayush26.event_management_admin.Activities.AdminHome;
import com.ayush26.event_management_admin.Activities.AdminRegister;
import com.ayush26.event_management_admin.Activities.RegistrationActivity;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.FragmentLoginBinding;
import com.google.android.material.button.MaterialButton;

import java.util.zip.Inflater;


public class LoginFragment extends Fragment {

    FragmentLoginBinding loginBinding;
    TextView registerTV;
    MaterialButton loginButton;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        loginBinding = FragmentLoginBinding.inflate(getLayoutInflater());

        registerTV = view.findViewById(R.id.login_to_register);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG", "onClick: clicked on Register ");
                view.getContext().startActivity(new Intent(view.getContext(), AdminRegister.class));
            }
        });

        loginButton = view.findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AdminHome.class));
            }
        });
        return view;
    }
}