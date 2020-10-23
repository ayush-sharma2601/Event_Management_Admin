package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayush26.event_management_admin.Models.APIModels.AddAgeGroupAPICall;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAPIModel;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityDivisionsCreationBinding;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.loadtoast.LoadToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisionsCreation extends AppCompatActivity {

    ActivityDivisionsCreationBinding divisionsCreationBinding;
    Button add, delete,save;
    TextInputEditText startAgeET,endAgeET,topicET;
    LoadToast loadToast;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        divisionsCreationBinding = ActivityDivisionsCreationBinding.inflate(getLayoutInflater());
        setContentView(divisionsCreationBinding.getRoot());

        divisionsCreationBinding.divisionNumber.setText(String.format("Division %s", i));

        final String currentCompetitionID = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("currentCompetitionID","noID");
        final String token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");

        add = findViewById(R.id.confirm_division_btn);
        save = findViewById(R.id.save_changes_btn);
        startAgeET = findViewById(R.id.start_age_field);
        endAgeET = findViewById(R.id.end_age_field);
        topicET = findViewById(R.id.topic_field);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Adding Division");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                String name = topicET.getText().toString().trim();
                String startAge = startAgeET.getText().toString().trim();
                String endAge = endAgeET.getText().toString().trim();

                Log.i("TAG", "onClick: >>>>>>>>>>>>>>>>>>> " + token);
                Log.i("TAG", "onClick: >>>>>>>>>>>>>>>>>>> " + currentCompetitionID);

                //api call to add age group
                Call<AddAgeGroupAPICall> call = RetrofitClient.getClient().AddAgeGroup(token,currentCompetitionID,startAge,endAge,name);
                call.enqueue(new Callback<AddAgeGroupAPICall>() {
                    @Override
                    public void onResponse(Call<AddAgeGroupAPICall> call, Response<AddAgeGroupAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                        if(response.body()!=null)
                        {
                            if (response.body().getSuccess()){
//                                Toast.makeText(DivisionsCreation.this,"Age group added successfully",Toast.LENGTH_SHORT).show();
                                loadToast.success();
                                divisionsCreationBinding.divisionNumber.setText(String.format("Division %s",++i));
                                startAgeET.setText("");
                                endAgeET.setText("");
                                topicET.setText("");

                            }
                            else
                                loadToast.error();
//                                Toast.makeText(DivisionsCreation.this,"Response Error",Toast.LENGTH_LONG).show();


                        }else
                            loadToast.error();
//                            Toast.makeText(DivisionsCreation.this,"Response Error",Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onFailure(Call<AddAgeGroupAPICall> call, Throwable t) {
                        Toast.makeText(DivisionsCreation.this,"Server Error",Toast.LENGTH_LONG).show();
                        loadToast.error();
                    }
                });
            }
        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Saving Information");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadToast.success();
                        startActivity(new Intent(DivisionsCreation.this,EventOnClick.class).putExtra("state","UPCOMING")
                                .putExtra("competition_id",currentCompetitionID));
                    }
                },1000);

            }
        });
    }
}