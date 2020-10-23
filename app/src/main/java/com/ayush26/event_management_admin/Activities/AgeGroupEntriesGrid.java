package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.SubmissionsAdapter;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionAPI;
import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.Models.SubmissionModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityAgeGroupEntriesGridBinding;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgeGroupEntriesGrid extends AppCompatActivity {
    ActivityAgeGroupEntriesGridBinding gridBinding;
    ArrayList<SubmissionModel> submissionModels;
    SubmissionsAdapter submissionsAdapter;
    String token ="";
    String ageGroupId="";
    String competitionID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridBinding = ActivityAgeGroupEntriesGridBinding.inflate(getLayoutInflater());
        setContentView(gridBinding.getRoot());

          token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");
         competitionID = getIntent().getStringExtra("group_competition_id");
          ageGroupId = getIntent().getStringExtra("age_group_id");
        Log.i("TAG", "onCreate: " +competitionID);

        getAgeGroupInfo(token,competitionID,ageGroupId);

        setRV();

        gridBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        final String
    }

    private void setRV() {
        submissionModels = new ArrayList<>();

        addData();
//        for(int i=0;i<200;i++){
//            submissionModels.add(new SubmissionModel("id1","https://firebasestorage.googleapis.com/v0/b/event-management-6acc9.appspot.com/o/illustration-cute-girl-lotus-position-reading-book_68196-2494.jpg?alt=media&token=6a4f37c2-f002-41c6-93a2-b41ce724fd0f"));
//
//        }


        submissionsAdapter = new SubmissionsAdapter(submissionModels,AgeGroupEntriesGrid.this,0);
        gridBinding.entriesGridRv.setLayoutManager(new GridLayoutManager(AgeGroupEntriesGrid.this,3, RecyclerView.VERTICAL,false));
        gridBinding.entriesGridRv.setAdapter(submissionsAdapter);
        submissionsAdapter.notifyDataSetChanged();
    }

    private void addData() {
        //take photos url from submission details url from firebase storage, compress them an
        Call<SubmissionAPI> call1 = RetrofitClient.getClient().getSubmissionsAdmin(token,ageGroupId);
        call1.enqueue(new Callback<SubmissionAPI>() {
            @Override
            public void onResponse(Call<SubmissionAPI> call1, Response<SubmissionAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        submissionModels.addAll(Arrays.asList(response.body().getSubmissions()));
                        submissionsAdapter.notifyDataSetChanged();
//                        Toast.makeText(AgeGroupEntriesGrid.this,"got entries",Toast.LENGTH_SHORT).show();

                    }
                    else
                        Toast.makeText(AgeGroupEntriesGrid.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(AgeGroupEntriesGrid.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SubmissionAPI> call, Throwable t) {
                Toast.makeText(AgeGroupEntriesGrid.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getAgeGroupInfo(String token, String competitionID, final String ageGroupId) {
        Log.i("TAG", "getAgeGroups: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ token);
        Call<GetAgeGroupsAPICall> call1 = RetrofitClient.getClient().getAgeGroupDetails(token,competitionID);
        call1.enqueue(new Callback<GetAgeGroupsAPICall>() {
            @Override
            public void onResponse(Call<GetAgeGroupsAPICall> call1, Response<GetAgeGroupsAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        AgeGroupCompetitionModel competitionModel = response.body().getAgeGroupCompetitionModels();
                        Log.i("TAG", "onResponse: >>>>>>>>>>>>> "+competitionModel.getAgeGroups().length);
                        for (DivisionModel divisionModel:competitionModel.getAgeGroups()){
                            if(divisionModel.getDivisionID().equals(ageGroupId)){
                                gridBinding.competitionName.setText(competitionModel.getTitle());
//                                gridBinding.category.setText(competitionModel.);
                                gridBinding.ageGroupEntryPage.setText(String.format("Entries for ages %s to %s",divisionModel.getStartAge(),divisionModel.getEndAge()));
                                gridBinding.entriesPageTopic.setText(divisionModel.getDivisionTopic());
                            }
                        }


                    }
                    else
                        Toast.makeText(AgeGroupEntriesGrid.this,"Response Error - No Success",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(AgeGroupEntriesGrid.this,"Response Error - no body",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<GetAgeGroupsAPICall> call, Throwable t) {
                Toast.makeText(AgeGroupEntriesGrid.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });
    }


}