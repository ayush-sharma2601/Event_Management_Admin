package com.ayush26.event_management_admin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayush26.event_management_admin.Activities.AdminHome;
import com.ayush26.event_management_admin.Activities.JudgeHome;
import com.ayush26.event_management_admin.Models.APIModels.LoginAPICall;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.FragmentJudgeLoginBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.loadtoast.LoadToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JudgeLogin extends Fragment {

    View view;
    FragmentJudgeLoginBinding judgeLoginBinding;
    MaterialButton loginBtm;
    TextInputEditText codeET;
    LoadToast loadToast;
    SharedPreferences sharedPreferences;
    String code ="";


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_judge_login, container, false);

        sharedPreferences = view.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        loginBtm = view.findViewById(R.id.login_judge);
        codeET = view.findViewById(R.id.code_field);

        loginBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Logging in");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                if(codeET.getText().toString().isEmpty()){
                    codeET.setError("Please Enter Your Code");
                    codeET.requestFocus();
                }
                if(codeET.getError()==null){
                    code = codeET.getText().toString().trim();
                    judgeLogin(code);
                }
            }
        });
        return view;
    }

    private void judgeLogin(String judgeCode) {
        Call<LoginAPICall> call = RetrofitClient.getClient().JudgeLogin(judgeCode);
        call.enqueue(new Callback<LoginAPICall>() {
            @Override
            public void onResponse(Call<LoginAPICall> call, Response<LoginAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if (response.body()!=null)
                {
                    if (response.body().getSuccess()){
//                        Toast.makeText(view.getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                            loadToast.success();
                        codeET.setText("");

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("judgeCode",response.body().getToken());
                        editor.commit();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getContext(), JudgeHome.class));
                            }
                        },1000);


                        //6dae0a0b
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