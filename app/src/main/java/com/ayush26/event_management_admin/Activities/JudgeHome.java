package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.SubmissionsAdapter;
import com.ayush26.event_management_admin.Models.APIModels.AgeGroupCodeAPI;
import com.ayush26.event_management_admin.Models.APIModels.SubmissionAPI;
import com.ayush26.event_management_admin.Models.SubmissionModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityJudgeHomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JudgeHome extends AppCompatActivity {

    ActivityJudgeHomeBinding judgeHomeBinding;
    String judgeCode="";
    ArrayList<SubmissionModel> submissionModels;
    SubmissionsAdapter submissionsAdapter;
    ImageView logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        judgeHomeBinding = ActivityJudgeHomeBinding.inflate(getLayoutInflater());
        setContentView(judgeHomeBinding.getRoot());
        logoutBtn = findViewById(R.id.judge_logout_btn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences logoutPreference = getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = logoutPreference.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(JudgeHome.this,"Successfully logged out",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        judgeCode = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("judgeCode","noCode");

        setRV();

        setInfo(judgeCode);
    }

    private void setRV() {
        submissionModels = new ArrayList<>();
        submissionsAdapter = new SubmissionsAdapter(submissionModels,JudgeHome.this,1);
        judgeHomeBinding.judgeEntriesGridRv.setLayoutManager(new GridLayoutManager(JudgeHome.this,3, RecyclerView.VERTICAL,false));
        judgeHomeBinding.judgeEntriesGridRv.setAdapter(submissionsAdapter);
        submissionsAdapter.notifyDataSetChanged();
    }

    private void setInfo(String judgeCode) {
        Log.i("TAG", "onLongClick: >>>>>>>>> judge code: "+judgeCode );
        Call<SubmissionAPI> call1 = RetrofitClient.getClient().getSubmissionsJudge(judgeCode);
        call1.enqueue(new Callback<SubmissionAPI>() {
            @Override
            public void onResponse(Call<SubmissionAPI> call1, Response<SubmissionAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        submissionModels.addAll(Arrays.asList(response.body().getSubmissions()));
                        submissionsAdapter.notifyDataSetChanged();
                    }
                    else
                        Toast.makeText(JudgeHome.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(JudgeHome.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SubmissionAPI> call, Throwable t) {
                Toast.makeText(JudgeHome.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });

    }
}