package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionModelAPI;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionAPI;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityDecideWinnerBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DecideWinner extends AppCompatActivity {

    ActivityDecideWinnerBinding binding;
    String token ="";
    String ageGroupId="";
    String competitionID="";
    String first="";
    String second ="";
    String third="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDecideWinnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");
        competitionID = getIntent().getStringExtra("group_competition_id");
        ageGroupId = getIntent().getStringExtra("age_group_id");
        
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setExistingResults();
        
        binding.previewResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillImageViews();
            }
        });
        
        binding.finalizeResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWinnersAPI();
            }
        });


    }

    private void setExistingResults() {
        Call<SingleCompetitionAPICall> call = RetrofitClient.getClient().getCompetitionDetails(token,competitionID);
        call.enqueue(new Callback<SingleCompetitionAPICall>() {
            @Override
            public void onResponse(Call<SingleCompetitionAPICall> call, Response<SingleCompetitionAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.code()==200){
//                        Toast.makeText(EventOnClick.this,"Information saved",Toast.LENGTH_SHORT).show();
                        SingleCompetitionModelAPI competitionBody = response.body().getCompetitionAPIModel();
                        for (DivisionModel divisionModel : competitionBody.getAgeGroups()){
                            if(divisionModel.getDivisionID().equals(ageGroupId)){

                                if (divisionModel.getWinners()==null){
                                    Log.i("TAG", "onResponse: >>>>>>>>>>>>> got null");

                                }
                                else {
//                                    Log.i("TAG", "onResponse: "+divisionModel.getWinners());
                                    binding.firstPosImgUrlField.setText(divisionModel.getWinners().getFirst());
                                    binding.secondPosImgUrlField.setText(divisionModel.getWinners().getSecond());
                                    binding.thirdPosImgUrlField.setText(divisionModel.getWinners().getThird());


                                }



                                    fillImageViews();
                                }
                        }
                    }
                    else
                        Toast.makeText(DecideWinner.this,"Response Error",Toast.LENGTH_LONG).show();


                }else
                    Toast.makeText(DecideWinner.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SingleCompetitionAPICall> call, Throwable t) {
                Toast.makeText(DecideWinner.this,"Server Error1",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setWinnersAPI() {

        if (FieldCheck()){
            Call<APICall> call1 = RetrofitClient.getClient().addWinners(token,competitionID,ageGroupId,first,second,third);
            call1.enqueue(new Callback<APICall>() {
                @Override
                public void onResponse(Call<APICall> call1, Response<APICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                    if(response.body()!=null)
                    {
                        if (response.body().getSuccess()){
                        Toast.makeText(DecideWinner.this,"Added Successfully",Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(DecideWinner.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                    }else
                        Toast.makeText(DecideWinner.this,"Response Error - no body",Toast.LENGTH_LONG).show();


                }

                @Override
                public void onFailure(Call<APICall> call, Throwable t) {
                    Toast.makeText(DecideWinner.this,"Server Error2",Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    private void fillImageViews() {
        if(FieldCheck()){
            binding.firstPosImg.setPadding(0,0,0,0);
            binding.secondPosImg.setPadding(0,0,0,0);
            binding.thirdPosImg.setPadding(0,0,0,0);

            Glide.with(DecideWinner.this).load(first).apply(new RequestOptions().override(100,100)).into(binding.firstPosImg);
            Glide.with(DecideWinner.this).load(second).apply(new RequestOptions().override(100,100)).into(binding.secondPosImg);
            Glide.with(DecideWinner.this).load(third).apply(new RequestOptions().override(100,100)).into(binding.thirdPosImg);
        }

    }

    private boolean FieldCheck()
    {
        first = binding.firstPosImgUrlField.getText().toString().trim();
        second = binding.secondPosImgUrlField.getText().toString().trim();
        third = binding.thirdPosImgUrlField.getText().toString().trim();

        if (first.isEmpty()){
            binding.firstPosImgUrlField.setError("field Empty");
            binding.firstPosImgUrlField.requestFocus();
        }
        if (second.isEmpty()){
            binding.secondPosImgUrlField.setError("field Empty");
            binding.secondPosImgUrlField.requestFocus();
        }
        if (third.isEmpty()){
            binding.thirdPosImgUrlField.setError("field Empty");
            binding.thirdPosImgUrlField.requestFocus();
        }
        return binding.firstPosImgUrlField.getError() == null && binding.secondPosImgUrlField.getError() == null && binding.thirdPosImgUrlField.getError() == null;
    }
}