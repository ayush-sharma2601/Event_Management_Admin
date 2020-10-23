package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.DivisionsRVAdapter;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAPIModel;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.Models.APIModels.LoginAPICall;
import com.ayush26.event_management_admin.Models.CompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityCreateNewEventBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewEvent extends AppCompatActivity {

    ActivityCreateNewEventBinding eventBinding;
    Button createDivBtn,nextBtn;
    Calendar calendar=Calendar.getInstance();
    TextInputEditText dateET,startTimeET,endTimeET,nameET,instructionsET,feesET,firstET,secondET,thirdET,genreET;
    String date,startTime,endTime,name,instructions,fees,first,second,third,genre,apiStartTime,apiEndTime,apiDate;
    SharedPreferences sharedPreferences;
    LoadToast loadToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBinding = ActivityCreateNewEventBinding.inflate(getLayoutInflater());
        setContentView(eventBinding.getRoot());

        attachID();
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        eventBinding.cutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateNewEvent.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        startTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateNewEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeET.setText( selectedHour + ":" + selectedMinute);
                        apiStartTime = selectedHour+":"+selectedMinute+":00";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endTimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateNewEvent.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeET.setText( selectedHour + ":" + selectedMinute);
                        apiEndTime = selectedHour+":"+selectedMinute+":00";
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        createDivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Saving Event");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                if(FieldsCheck())
                {
//                    Toast.makeText(CreateNewEvent.this,"Fields saved",Toast.LENGTH_SHORT).show();
                    CreateDivsCall();
                }
                else {
//                    Toast.makeText(CreateNewEvent.this,"Internal Error",Toast.LENGTH_SHORT).show();

                }

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Creating Event");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                if (FieldsCheck())
                    CreateEventCall();
                else {
//                    Toast.makeText(CreateNewEvent.this,"Internal Error",Toast.LENGTH_SHORT).show();

                }
//                startActivity(new Intent(CreateNewEvent.this,NewEventGroupDivision.class));
            }
        });
    }

    private void CreateEventCall() {
        //API call for creating event
        final String startDate = apiDate+" "+apiStartTime;
        final String endDate = apiDate+" "+apiEndTime;
//        final JSONArray winnerRewards = new JSONArray();
//        winnerRewards.put(first);
//        winnerRewards.put(second);
//        winnerRewards.put(third);
        final String token1 = sharedPreferences.getString("token","no token");

        Log.i("TAG", "CreateEventCall: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ token1);
        Call<CompetitionAddAPI> call = RetrofitClient.getClient().AddCompetition(token1,name,instructions,startDate,endDate,first,second,third,Integer.parseInt(fees),genre);
        call.enqueue(new Callback<CompetitionAddAPI>() {
            @Override
            public void onResponse(Call<CompetitionAddAPI> call, Response<CompetitionAddAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
//                        Toast.makeText(CreateNewEvent.this,"Event Created Successfully",Toast.LENGTH_LONG).show();
                        loadToast.success();
                        CompetitionAPIModel competitionBody = response.body().getCompetitionAPIModel();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("currentCompetitionID",competitionBody.getId());
                        editor.commit();
                        startActivity(new Intent(CreateNewEvent.this,EventOnClick.class).putExtra("state","UPCOMING")
                                .putExtra("competition_id",competitionBody.getId()));

                    }
                    else
                        loadToast.error();
//                        Toast.makeText(CreateNewEvent.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    loadToast.error();
//                    Toast.makeText(CreateNewEvent.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<CompetitionAddAPI> call, Throwable t) {
                Toast.makeText(CreateNewEvent.this,"Server Error",Toast.LENGTH_LONG).show();
            }
        });



    }

    private void CreateDivsCall() {
        //API call for creating event
        final String startDate = apiDate+" "+apiStartTime;
        final String endDate = apiDate+" "+apiEndTime;

        final String token = sharedPreferences.getString("token","no token");

        Log.i("TAG", "CreateEventCall: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ token);
        Call<CompetitionAddAPI> call = RetrofitClient.getClient().AddCompetition(token,name,instructions,startDate,endDate,first,second,third,Integer.parseInt(fees),genre);
        call.enqueue(new Callback<CompetitionAddAPI>() {
            @Override
            public void onResponse(Call<CompetitionAddAPI> call, Response<CompetitionAddAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
//                        Toast.makeText(CreateNewEvent.this,"Information saved",Toast.LENGTH_SHORT).show();
                        loadToast.success();
                        CompetitionAPIModel competitionBody = response.body().getCompetitionAPIModel();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("currentCompetitionID",competitionBody.getId());
                        editor.commit();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(CreateNewEvent.this,DivisionsCreation.class).putExtra("purpose","create"));
                            }
                        },1000);

                    }
                    else
                        loadToast.error();
//                        Toast.makeText(CreateNewEvent.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    loadToast.error();
//                    Toast.makeText(CreateNewEvent.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<CompetitionAddAPI> call, Throwable t) {
                Toast.makeText(CreateNewEvent.this,"Server Error",Toast.LENGTH_LONG).show();
                loadToast.error();
            }
        });



    }


    private boolean FieldsCheck() {
        name = nameET.getText().toString();
        startTime = startTimeET.getText().toString();
        endTime = endTimeET.getText().toString();
        date = dateET.getText().toString();
        instructions = instructionsET.getText().toString();
        genre = genreET.getText().toString();
        fees = feesET.getText().toString();
        first = firstET.getText().toString();
        second = secondET.getText().toString();
        third = thirdET.getText().toString();

        if(name.isEmpty()){
            nameET.setError("Field empty");
            nameET.requestFocus();
        }
        if(startTime.isEmpty()){
            startTimeET.requestFocus();
        }if(endTime.isEmpty()){
            endTimeET.requestFocus();
        }if(date.isEmpty()){
            dateET.requestFocus();
        }if(instructions.isEmpty()){
            instructionsET.setError("Field empty");
            instructionsET.requestFocus();
        }if(genre.isEmpty()){
            genreET.setError("Field empty");
            genreET.requestFocus();
        }if(fees.isEmpty()){
            feesET.setError("Field empty");
            feesET.requestFocus();
        }if(first.isEmpty()){
            firstET.setError("Field empty");
            firstET.requestFocus();
        }if(second.isEmpty()){
            secondET.setError("Field empty");
            secondET.requestFocus();
        }if(third.isEmpty()){
            thirdET.setError("Field empty");
            thirdET.requestFocus();
        }
        return nameET.getError() == null && dateET.getError() == null && startTimeET.getError() == null && endTimeET.getError() == null
                && instructionsET.getError() == null && feesET.getError() == null && genreET.getError() == null && firstET.getError() == null
                && secondET.getError() == null && thirdET.getError() == null;
    }

//    private void SetDivisionsRV() {
//        divisionsRVAdapter = new DivisionsRVAdapter(divisionRVModels,CreateNewEvent.this,1);
//        eventBinding.divisionsRv.setLayoutManager(new LinearLayoutManager(CreateNewEvent.this,LinearLayoutManager.VERTICAL,false));
//        eventBinding.divisionsRv.setAdapter(divisionsRVAdapter);
//        divisionsRVAdapter.notifyDataSetChanged();
//    }



    void attachID()
    {
        dateET = findViewById(R.id.new_event_date_field);
        startTimeET = findViewById(R.id.new_event_start_time_field);
        endTimeET = findViewById(R.id.new_event_end_time_field);
        createDivBtn = findViewById(R.id.create_divisions_btn);
        nameET = findViewById(R.id.new_event_name_field);
        instructionsET = findViewById(R.id.new_event_instructions_field);
        genreET = findViewById(R.id.new_event_genre_field);
        feesET = findViewById(R.id.new_event_fees_field);
        firstET = findViewById(R.id.new_event_first_pos_field);
        secondET = findViewById(R.id.new_event_second_pos_field);
        thirdET = findViewById(R.id.new_event_third_pos_field);
        nextBtn = findViewById(R.id.initial_create_next_btn);

    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        String apiFormat = "yyyy-MM-dd";//format for api call
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat apiSDF = new SimpleDateFormat(apiFormat,Locale.US);
        apiDate = apiSDF.format(calendar.getTime());
        eventBinding.newEventDateField.setText(sdf.format(calendar.getTime()));
    }



}

