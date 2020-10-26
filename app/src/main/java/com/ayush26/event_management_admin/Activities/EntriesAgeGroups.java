package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.DivisionsRVAdapter;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityEntriesAgeGroupsBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntriesAgeGroups extends AppCompatActivity {

    ActivityEntriesAgeGroupsBinding binding;
    DivisionsRVAdapter divisionsRVAdapter;
    ImageButton backBtn;
    int code;
    ArrayList<DivisionRVModel> divisionRVModels;
    RecyclerView divisionsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntriesAgeGroupsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backBtn = findViewById(R.id.back_btn);

        final String competitionID = getIntent().getStringExtra("competition_id");
        final String token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");
        final String purpose = getIntent().getStringExtra("purpose");

        if (purpose.equals("open"))
            code=2;
        else if(purpose.equals("close"))
            code=4;
        else code=2;

        setupRV(code);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getInfo(competitionID,token);
    }

    private void getInfo(final String competitionID, String token) {
        //api call to get age groups info
        Log.i("TAG", "getAgeGroups: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ token);
        Log.i("TAG", "getAgeGroups: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ competitionID);
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
                            divisionRVModels.add(new DivisionRVModel(divisionModel.getDivisionTopic(),Integer.toString(divisionModel.getParticipantNum().length),competitionID,divisionModel.getDivisionID()));
                            divisionsRVAdapter.notifyDataSetChanged();
                        }


                    }
                    else
                        Toast.makeText(EntriesAgeGroups.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(EntriesAgeGroups.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<GetAgeGroupsAPICall> call, Throwable t) {
                Toast.makeText(EntriesAgeGroups.this,"Server Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupRV(int code) {
        binding.entrieDivisionsRv.setLayoutManager(new LinearLayoutManager(EntriesAgeGroups.this,LinearLayoutManager.VERTICAL,false));
        divisionRVModels = new ArrayList<>();
        divisionsRVAdapter = new DivisionsRVAdapter(divisionRVModels,EntriesAgeGroups.this,code);
        binding.entrieDivisionsRv.setAdapter(divisionsRVAdapter);
        divisionsRVAdapter.notifyDataSetChanged();
    }
}