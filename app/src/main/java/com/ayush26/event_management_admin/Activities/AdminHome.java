package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.EventsRVAdapter;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAPIModel;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionGetAPI;
import com.ayush26.event_management_admin.Models.CompetitionModel;
import com.ayush26.event_management_admin.Models.EventRVItem;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityAdminHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHome extends AppCompatActivity {

    ActivityAdminHomeBinding adminHomeBinding;
    ArrayList<EventRVItem> upcomingEvents,pastEvents;
    EventsRVAdapter pastEventAdapter,upcomingEventsAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminHomeBinding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(adminHomeBinding.getRoot());

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        setLayoutManager();

        initArrayList();

        initAdapter();

        addData();

        attachAdapter();

        updateData();

        adminHomeBinding.adminLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().commit();
                finish();
            }
        });

        adminHomeBinding.newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHome.this,CreateNewEvent.class));
            }
        });

    }

    private void updateData() {
        upcomingEventsAdapter.notifyDataSetChanged();
        pastEventAdapter.notifyDataSetChanged();
    }

    private void attachAdapter() {
        adminHomeBinding.upcomingEventsRv.setAdapter(upcomingEventsAdapter);
        adminHomeBinding.pastEventsRv.setAdapter(pastEventAdapter);
    }

    private void addData() {
        //api call to get all competitions
        final String token = sharedPreferences.getString("token","no token");
        Log.i("TAG", "addData: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+token);
        Call<CompetitionGetAPI> call = RetrofitClient.getClient().GetCompetitions(token);
        call.enqueue(new Callback<CompetitionGetAPI>() {
            @Override
            public void onResponse(Call<CompetitionGetAPI> call, Response<CompetitionGetAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
                        Toast.makeText(AdminHome.this,"Welcome",Toast.LENGTH_SHORT).show();
                        CompetitionModel[] competitions = response.body().getCompetitionModels();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        for (CompetitionModel competitionModel : competitions){
                            if (competitionModel.getState().equals("UPCOMING") || competitionModel.getState().equals("ONGOING")){
                                EventRVItem temp = new EventRVItem(competitionModel.getId(),competitionModel.getTitle(),competitionModel.getStartDate(),competitionModel.getGenre(),competitionModel.getState());
                                upcomingEvents.add(temp);
                                upcomingEventsAdapter.notifyDataSetChanged();
//                                upcomingEventsAdapter.AddEvent(temp);
                                Log.i("TAG", "onResponse: >>>>>>>>>>>>>>>>>>>>>> "+ competitionModel.getId());
                            }
                            else if(competitionModel.getState().equals("COMPLETED")){
                                EventRVItem temp = new EventRVItem(competitionModel.getId(),competitionModel.getTitle(),competitionModel.getStartDate(),competitionModel.getGenre(),"COMPLETED");
                                pastEvents.add(temp);
                                pastEventAdapter.notifyDataSetChanged();
                                Log.i("TAG", "onResponse: >>>>>>>>>>>>>>>>>>>>>> "+ temp.getEventName());
                            }
                        }

                    }
                    else
                        Toast.makeText(AdminHome.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(AdminHome.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<CompetitionGetAPI> call, Throwable t) {
                Toast.makeText(AdminHome.this,"Server Error",Toast.LENGTH_LONG).show();
            }
        });
//        pastEvents.add(new EventRVItem("National Youth Art Competition","17th December,2020","Drawing","COMPLETED"));

    }

    private void initAdapter() {
        upcomingEventsAdapter = new EventsRVAdapter(upcomingEvents,this,0);
        pastEventAdapter = new EventsRVAdapter(pastEvents,this,1);
    }

    private void initArrayList() {
        upcomingEvents = new ArrayList<>();
        pastEvents = new ArrayList<>();
    }

    private void setLayoutManager() {
        adminHomeBinding.upcomingEventsRv
                .setLayoutManager(new LinearLayoutManager(AdminHome.this,LinearLayoutManager.VERTICAL,false));
        adminHomeBinding.pastEventsRv
                .setLayoutManager(new LinearLayoutManager(AdminHome.this,LinearLayoutManager.VERTICAL,false));
    }


}