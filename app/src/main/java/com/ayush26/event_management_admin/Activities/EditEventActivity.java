package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAPIModel;
import com.ayush26.event_management_admin.Models.APIModels.CompetitionAddAPI;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionAPICall;
import com.ayush26.event_management_admin.Models.APIModels.SingleCompetitionModelAPI;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityEditEventBinding;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.loadtoast.LoadToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventActivity extends AppCompatActivity {

    ActivityEditEventBinding editEventBinding;
    String token="";
    String tempCompID ="";
    Calendar calendar=Calendar.getInstance();
    String date,startTime,endTime,name,instructions,fees,first,second,third,genre,apiStartTime,apiEndTime,apiDate;
    String startDate,endDate;
    LoadToast loadToast;
    TextInputEditText dateET,startTimeET,endTimeET,nameET,instructionsET,feesET,firstET,secondET,thirdET,genreET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editEventBinding = ActivityEditEventBinding.inflate(getLayoutInflater());
        setContentView(editEventBinding.getRoot());

        attachID();

        tempCompID = getIntent().getStringExtra("edit_competition_id");
        token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");

        setupPickers();

        fillAlreadyExistingInfo(tempCompID);

        editEventBinding.saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Saving Changes");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();
                if (FieldsCheck()){
                    updateCompetiton(tempCompID);
                }

            }
        });

        editEventBinding.editDivisionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to edit age groups page
                startActivity(new Intent(EditEventActivity.this,EditDivisionActivity.class)
                .putExtra("competition_id",tempCompID));

            }
        });


    }

    private void updateCompetiton(String tempCompID) {
        final String startDate = apiDate+" "+apiStartTime;
        final String endDate = apiDate+" "+apiEndTime;

        Log.i("TAG", "CreateEventCall: >>>>>>>>>>>>>>>>>>>>>>>>>> "+ token);
        Call<APICall> call = RetrofitClient.getClient().updateCompetitionGeneral(token,tempCompID,name,instructions,startDate,endDate,first,second,third,Integer.parseInt(fees),genre);
        call.enqueue(new Callback<APICall>() {
            @Override
            public void onResponse(Call<APICall> call, Response<APICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.body().getSuccess()){
//                        Toast.makeText(EditEventActivity.this,"Competition Updated successfully",Toast.LENGTH_SHORT).show();
                        loadToast.success();
                        startActivity(new Intent(EditEventActivity.this,AdminHome.class));
                        finish();

                    }
                    else
                        loadToast.error();
//                        Toast.makeText(EditEventActivity.this,"Empty Request",Toast.LENGTH_LONG).show();

                }else
                    loadToast.error();
                    Toast.makeText(EditEventActivity.this,"Fill all fields correctly",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<APICall> call, Throwable t) {
//                Toast.makeText(EditEventActivity.this,"Server Error",Toast.LENGTH_LONG).show();
                loadToast.error();
            }
        });
    }

    private void attachID() {
        dateET = findViewById(R.id.edit_event_date_field);
        startTimeET = findViewById(R.id.edit_event_start_time_field);
        endTimeET = findViewById(R.id.edit_event_end_time_field);
        nameET = findViewById(R.id.edit_event_name_field);
        instructionsET = findViewById(R.id.edit_event_instructions_field);
        genreET = findViewById(R.id.edit_event_genre_field);
        feesET = findViewById(R.id.edit_event_fees_field);
        firstET = findViewById(R.id.edit_event_first_pos_field);
        secondET = findViewById(R.id.edit_event_second_pos_field);
        thirdET = findViewById(R.id.edit_event_third_pos_field);
    }

    private void setupPickers() {
        editEventBinding.cutButton.setOnClickListener(new View.OnClickListener() {
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
                new DatePickerDialog(EditEventActivity.this, date, calendar
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
                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(EditEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

    }

    private void fillAlreadyExistingInfo(String tempCompID) {
        Log.i("TAG", "onResponse:>>>>>>>>>>>>>>>>>>  "+ tempCompID);
        Log.i("TAG", "onResponse:>>>>>>>>>>>>>>>>>>  "+ token);

        Call<SingleCompetitionAPICall> call = RetrofitClient.getClient().getCompetitionDetails(token,tempCompID);
        call.enqueue(new Callback<SingleCompetitionAPICall>() {
            @Override
            public void onResponse(Call<SingleCompetitionAPICall> call, Response<SingleCompetitionAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                if(response.body()!=null)
                {
                    if (response.code()==200){
//                        Toast.makeText(EventOnClick.this,"Information saved",Toast.LENGTH_SHORT).show();
                        SingleCompetitionModelAPI competitionBody = response.body().getCompetitionAPIModel();
                        editEventBinding.editEventNameField.setText(competitionBody.getTitle());
                        editEventBinding.editEventGenreField.setText(competitionBody.getGenre());
                        editEventBinding.editEventFeesField.setText(Integer.toString(competitionBody.getFee()));
                        editEventBinding.editEventFirstPosField.setText(String.format("%s", competitionBody.getWinnerRewards()[0]));
                        editEventBinding.editEventSecondPosField.setText(String.format("%s", competitionBody.getWinnerRewards()[1]));
                        editEventBinding.editEventThirdPosField.setText(String.format("%s", competitionBody.getWinnerRewards()[2]));
                        editEventBinding.editEventInstructionsField.setText(competitionBody.getInstructions());


                    }
                    else
                        Toast.makeText(EditEventActivity.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(EditEventActivity.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SingleCompetitionAPICall> call, Throwable t) {
                Toast.makeText(EditEventActivity.this,"Server Error1",Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getDate(String DTF){
        if(DTF!=null){
            String date = DTF.substring(0,4)+"-"+DTF.substring(5,7)+"-"+DTF.substring(8,10);
            return date;
        }else
            return "false";
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
                && instructionsET.getError() == null && feesET.getError() == null &&
                genreET.getError() == null && firstET.getError() == null
                && secondET.getError() == null && thirdET.getError() == null;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        String apiFormat = "yyyy-MM-dd";//format for api call
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat apiSDF = new SimpleDateFormat(apiFormat,Locale.US);
        apiDate = apiSDF.format(calendar.getTime());
        editEventBinding.editEventDateField.setText(sdf.format(calendar.getTime()));
    }

}