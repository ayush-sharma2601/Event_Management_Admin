package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.DivisionsRVAdapter;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.databinding.ActivityCreateNewEventBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CreateNewEvent extends AppCompatActivity {

    ActivityCreateNewEventBinding eventBinding;
    Button createDivBtn;
    Calendar calendar=Calendar.getInstance();
    TextInputEditText dateET,startTimeET,endTimeET,divsET;
    ArrayList<DivisionRVModel> divisionRVModels=new ArrayList<>();
    DivisionsRVAdapter divisionsRVAdapter;
    int divNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBinding = ActivityCreateNewEventBinding.inflate(getLayoutInflater());
        setContentView(eventBinding.getRoot());

        attachID();

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
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        createDivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String divsNumber = divsET.getText().toString().trim();
                if(divsNumber.isEmpty())
                    Toast.makeText(CreateNewEvent.this,"No value for divisions provided",Toast.LENGTH_SHORT).show();
                else {
                    divNum = Integer.parseInt(divsNumber);
                    Toast.makeText(CreateNewEvent.this,Integer.toString(divNum),Toast.LENGTH_SHORT).show();
                    FillDivisionsRV(divNum);

                }

            }
        });

        eventBinding.initialCreateNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewEvent.this,NewEventGroupDivision.class));
            }
        });
    }

    private void FillDivisionsRV(int divs) {
        for (int i=1;i<=divs;i++)
            divisionRVModels.add(new DivisionRVModel("Division "+ Integer.toString(divs)));

        divisionsRVAdapter = new DivisionsRVAdapter(divisionRVModels,CreateNewEvent.this,1);
        eventBinding.divisionsRv.setLayoutManager(new LinearLayoutManager(CreateNewEvent.this,LinearLayoutManager.VERTICAL,false));
        eventBinding.divisionsRv.setAdapter(divisionsRVAdapter);
        divisionsRVAdapter.notifyDataSetChanged();

    }

    void attachID()
    {
        dateET = findViewById(R.id.new_event_date_field);
        startTimeET = findViewById(R.id.new_event_start_time_field);
        endTimeET = findViewById(R.id.new_event_end_time_field);
        createDivBtn = findViewById(R.id.create_divisions_btn);
        divsET = findViewById(R.id.new_event_divisions_field);
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eventBinding.newEventDateField.setText(sdf.format(calendar.getTime()));
    }
}

