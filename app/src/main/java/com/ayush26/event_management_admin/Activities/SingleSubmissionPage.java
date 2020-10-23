package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ayush26.event_management_admin.Models.APIModels.ContactModelAPI;
import com.ayush26.event_management_admin.Models.APIModels.ParticipantDetailAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionAPI;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionDetailAPICall;
import com.ayush26.event_management_admin.Models.ParticipantModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivitySingleSubmissionPageBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.ContentHandler;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleSubmissionPage extends AppCompatActivity {

    ActivitySingleSubmissionPageBinding pageBinding;
    String participantID="";
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageBinding = ActivitySingleSubmissionPageBinding.inflate(getLayoutInflater());
        setContentView(pageBinding.getRoot());

        pageBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final String photoURL = getIntent().getStringExtra("photo_url");
        final String submissionID = getIntent().getStringExtra("submission_id");
        String purpose = getIntent().getStringExtra("purpose");

        Glide.with(SingleSubmissionPage.this).load(photoURL).into(pageBinding.participantSubmissionImage);

        if (purpose.equals("judge"))
        {
            //call judge apis
            token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("judgeCode","noToken");
            setJudgeInfo(token,submissionID);
        }
        else {
            //call admin apis
            token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");
            setAdminInfo(token,submissionID);
        }


    }

    private void setAdminInfo(final String token, String submissionID) {
        // to get participant id from submission id
        Call<SubmissionDetailAPICall> call1 = RetrofitClient.getClient().adminSubmissionDetails(token,submissionID);
        call1.enqueue(new Callback<SubmissionDetailAPICall>() {
            @Override
            public void onResponse(Call<SubmissionDetailAPICall> call1, Response<SubmissionDetailAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        ParticipantModel participantModel = response.body().getDetailSubmissionModel().getParticipant();
                        pageBinding.participantName.setText(participantModel.getName());
                        pageBinding.participantAge.setText(String.format("%s",participantModel.getAge()));
                        participantID = participantModel.getParticipantID();
                        setPhoneNumber(token,participantID);

                    }
                    else
                        Toast.makeText(SingleSubmissionPage.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(SingleSubmissionPage.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SubmissionDetailAPICall> call, Throwable t) {
                Toast.makeText(SingleSubmissionPage.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setPhoneNumber(String token, String participantID) {
        // to get participant contact details from participant id
        Call<ParticipantDetailAPICall> call2 = RetrofitClient.getClient().getParticipantDetailsAdmin(token,participantID);
        call2.enqueue(new Callback<ParticipantDetailAPICall>() {
            @Override
            public void onResponse(Call<ParticipantDetailAPICall> call1, Response<ParticipantDetailAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        ContactModelAPI contactModel = response.body().getContactDetails();
                        pageBinding.participantPhone.setText(contactModel.getPhoneNumber());
                    }
                    else
                        Toast.makeText(SingleSubmissionPage.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(SingleSubmissionPage.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<ParticipantDetailAPICall> call, Throwable t) {
                Toast.makeText(SingleSubmissionPage.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setJudgeInfo(final String token, String submissionID) {
        // to get participant id from submission id
        Call<SubmissionDetailAPICall> call1 = RetrofitClient.getClient().judgeSubmissionDetails(token,submissionID);
        call1.enqueue(new Callback<SubmissionDetailAPICall>() {
            @Override
            public void onResponse(Call<SubmissionDetailAPICall> call1, Response<SubmissionDetailAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        ParticipantModel participantModel = response.body().getDetailSubmissionModel().getParticipant();
                        pageBinding.participantName.setText(participantModel.getName());
                        pageBinding.participantAge.setText(String.format("%s",participantModel.getAge()));
                        participantID = participantModel.getParticipantID();
                        setJudgePhoneNumber(token,participantID);

                    }
                    else
                        Toast.makeText(SingleSubmissionPage.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(SingleSubmissionPage.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SubmissionDetailAPICall> call, Throwable t) {
                Toast.makeText(SingleSubmissionPage.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void setJudgePhoneNumber(String token, String participantID) {
        // to get participant contact details from participant id
        Call<ParticipantDetailAPICall> call2 = RetrofitClient.getClient().getParticipantDetailsJudge(token,participantID);
        call2.enqueue(new Callback<ParticipantDetailAPICall>() {
            @Override
            public void onResponse(Call<ParticipantDetailAPICall> call1, Response<ParticipantDetailAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        ContactModelAPI contactModel = response.body().getContactDetails();
                        pageBinding.participantPhone.setText(contactModel.getPhoneNumber());
                    }
                    else
                        Toast.makeText(SingleSubmissionPage.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(SingleSubmissionPage.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<ParticipantDetailAPICall> call, Throwable t) {
                Toast.makeText(SingleSubmissionPage.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });
    }


}