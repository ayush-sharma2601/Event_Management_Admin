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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.DivisionsRVAdapter;
import com.ayush26.event_management_admin.Models.APIModels.AddAgeGroupAPICall;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAPIModel;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionModelAPI;
import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityUpcomingEventBinding;
import com.google.android.material.textview.MaterialTextView;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventOnClick extends AppCompatActivity {

    ActivityUpcomingEventBinding upBind;
    Button editEventBtn,deleteEventBtn,startEventBtn,sendAlertBtn,stopEventBtn,seeEntriesBtn,viewResultBtn;
    GridLayout upcomingSet,ongoingSet;
    MaterialTextView name,collection;
    String eventState="";
    String competitionID = "";
    String token ="";
    DivisionsRVAdapter divisionsRVAdapter;
    ArrayList<DivisionRVModel> divisionRVModels;
    RecyclerView divisionsRV;
    SharedPreferences sharedPreferences;
    LoadToast loadToast;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EventOnClick.this,AdminHome.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        upBind = ActivityUpcomingEventBinding.inflate(getLayoutInflater());
        setContentView(upBind.getRoot());

        attachID();

        loadToast = new LoadToast(EventOnClick.this);
        loadToast.setText("Loading Information");
        loadToast.setTranslationY(100);
        loadToast.setBorderColor(R.color.color_accent);
        loadToast.show();

        eventState = getIntent().getStringExtra("state");
        Log.i("TAG", "onCreate: >>>>>>>>>>>>>>>>>>>>>>>> state = "+ eventState);
        competitionID = getIntent().getStringExtra("competition_id");
        sharedPreferences =  getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","no token");

        if(eventState.equals("UPCOMING")){
            upcomingSet.setVisibility(View.VISIBLE);
            ongoingSet.setVisibility(View.GONE);
            viewResultBtn.setVisibility(View.GONE);
        }
        else if (eventState.equals("COMPLETED")){
            upcomingSet.setVisibility(View.GONE);
            ongoingSet.setVisibility(View.GONE);
            viewResultBtn.setVisibility(View.GONE);
        }
        else if(eventState.equals("ONGOING")){
            upcomingSet.setVisibility(View.GONE);
            ongoingSet.setVisibility(View.VISIBLE);
            viewResultBtn.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(EventOnClick.this, "Issues in fetching data. Please try again later.", Toast.LENGTH_LONG).show();
            finish();
        }

        upBind.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to edit event screen
                Intent editEventIntent = new Intent(EventOnClick.this,EditEventActivity.class);
                editEventIntent.putExtra("edit_competition_id",competitionID);
                startActivity(editEventIntent);
                finish();
            }
        });

        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call to delete competition
            }
        });

        sendAlertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call to send alert to all particiopants
            }
        });

        startEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call to set status to "ONGOING"
                //on success do this
                startCompetition(competitionID);

            }
        });

        stopEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //api call to set status to "COMPLETED"
                //show results btn
                closeCompetition(competitionID);
            }
        });

        seeEntriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to age group division page
                startActivity(new Intent(EventOnClick.this,EntriesAgeGroups.class).putExtra("competition_id",competitionID));
            }
        });

        SetCompetitionInfo(competitionID);
        Log.i("TAG", "onCreate: >>>>>>>>>>>>>>>>>> created");
    }

    private void attachID() {
        //link IDs
        editEventBtn = findViewById(R.id.edit_event);
        deleteEventBtn = findViewById(R.id.delete_event);
        startEventBtn = findViewById(R.id.start_event);
        sendAlertBtn = findViewById(R.id.alert_event);
        stopEventBtn = findViewById(R.id.end_event);
        seeEntriesBtn = findViewById(R.id.entries_event);
        viewResultBtn = findViewById(R.id.view_result_btn);
        upcomingSet = findViewById(R.id.upcoming_btn_set);
        ongoingSet = findViewById(R.id.event_day_btn_set);
        name = findViewById(R.id.competition_name);
        divisionsRV = findViewById(R.id.registration_distribution_rv);
        collection = findViewById(R.id.competition_collection);
        //setup rv
        divisionRVModels = new ArrayList<>();
        divisionsRV.setLayoutManager(new LinearLayoutManager(EventOnClick.this,LinearLayoutManager.VERTICAL,false));
        divisionsRVAdapter = new DivisionsRVAdapter(divisionRVModels,EventOnClick.this,0);
        divisionsRV.setAdapter(divisionsRVAdapter);
        divisionsRVAdapter.notifyDataSetChanged();
    }

    private void SetCompetitionInfo(final String competitionID) {
        //api call for competition info
        Log.i("TAG", "onResponse:>>>>>>>>>>>>>>>>>>  "+ competitionID);
        Log.i("TAG", "onResponse:>>>>>>>>>>>>>>>>>>  "+ token);

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
                        upBind.competitionName.setText(competitionBody.getTitle());
                        upBind.competitionGenre.setText(competitionBody.getGenre());
                        upBind.competitionFees.setText(String.format("Rs %s",Integer.toString(competitionBody.getFee())));
                        upBind.date.setText(getDate(competitionBody.getStartDate()));
                        upBind.firstPrizeText.setText(String.format("First Prize is Rs %s", competitionBody.getWinnerRewards()[0]));
                        upBind.secondPrizeText.setText(String.format("Second Prize is Rs %s", competitionBody.getWinnerRewards()[1]));
                        upBind.thirdPrizeText.setText(String.format("Third Prize is Rs %s", competitionBody.getWinnerRewards()[2]));
                        upBind.competitionInstructionsField.setText(competitionBody.getInstructions());
                        upBind.startTime.setText(competitionBody.getStartDate().substring(11,16));
                        upBind.endTime.setText(competitionBody.getEndDate().substring(11,16));

                    }
                    else
                        loadToast.error();
//                        Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();


                }else
                    loadToast.error();
//                    Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SingleCompetitionAPICall> call, Throwable t) {
                Toast.makeText(EventOnClick.this,"Server Error1",Toast.LENGTH_LONG).show();
            }
        });


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
                        collection.setText(String.format("Rs %s",competitionModel.getTotalCollection()));
                        Log.i("TAG", "onResponse: >>>>>>>>>>>>> "+competitionModel.getAgeGroups().length);
                        for (DivisionModel divisionModel:competitionModel.getAgeGroups()){
                            divisionRVModels.add(new DivisionRVModel(divisionModel.getDivisionTopic(),
                                    Integer.toString(divisionModel.getParticipantNum().length),
                                    competitionID,
                                    divisionModel.getDivisionID()));
                            divisionsRVAdapter.notifyDataSetChanged();
                        }

                        loadToast.success();


                    }
                    else
                        loadToast.error();
//                        Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    loadToast.error();
//                    Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<GetAgeGroupsAPICall> call, Throwable t) {
                Toast.makeText(EventOnClick.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });

    }

    public String getDate(String DTF){
        if(DTF!=null){
            String month = getMonth(DTF.substring(5,7));
            String date = DTF.substring(8,10)+ " " + month+ ", "+ DTF.substring(0,4);
            return date;
        }else
            return "false";
    }

    private String getMonth(String substring) {
        return (substring.equals("01")?"January":(substring.equals("02")?"February":(substring.equals("03")?"March":(substring.equals("04")?"April":
                (substring.equals("05")?"May":(substring.equals("06")?"June":(substring.equals("07")?"July":(substring.equals("08")?"August":
                        (substring.equals("09")?"September":(substring.equals("10")?"October":(substring.equals("11")?"November":(substring.equals("12")?"December":"Default"))))))))))));
    }

    private void startCompetition(String id)
    {
        Call<AddAgeGroupAPICall> call = RetrofitClient.getClient().openCompetition(token,id);
        call.enqueue(new Callback<AddAgeGroupAPICall>() {
            @Override
            public void onResponse(Call<AddAgeGroupAPICall> call, Response<AddAgeGroupAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.isSuccessful()){
                        Toast.makeText(EventOnClick.this,response.message(),Toast.LENGTH_SHORT).show();
                        upcomingSet.setVisibility(View.GONE);
                        ongoingSet.setVisibility(View.VISIBLE);
                        viewResultBtn.setVisibility(View.GONE);
                    }
                    else
                        Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<AddAgeGroupAPICall> call, Throwable t) {
                Toast.makeText(EventOnClick.this,"Server Error1",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void closeCompetition(String id)
    {
        Call<AddAgeGroupAPICall> call = RetrofitClient.getClient().closeCompetition(token,id);
        call.enqueue(new Callback<AddAgeGroupAPICall>() {
            @Override
            public void onResponse(Call<AddAgeGroupAPICall> call, Response<AddAgeGroupAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.isSuccessful()){
                        Toast.makeText(EventOnClick.this,response.message(),Toast.LENGTH_SHORT).show();
                        upcomingSet.setVisibility(View.GONE);
                        ongoingSet.setVisibility(View.GONE);
                        viewResultBtn.setVisibility(View.VISIBLE);
                    }
                    else
                        Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(EventOnClick.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<AddAgeGroupAPICall> call, Throwable t) {
                Toast.makeText(EventOnClick.this,"Server Error1",Toast.LENGTH_LONG).show();
            }
        });
    }

}