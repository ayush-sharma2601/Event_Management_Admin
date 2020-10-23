package com.ayush26.event_management_admin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ayush26.event_management_admin.Adapters.EditDivisionsRVAdapter;
import com.ayush26.event_management_admin.Models.APIModels.AddAgeGroupAPICall;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityEditDivisionBinding;
import com.google.android.material.textfield.TextInputEditText;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDivisionActivity extends AppCompatActivity {

    ActivityEditDivisionBinding divisionBinding;
    EditDivisionsRVAdapter editDivisionsRVAdapter;
    ArrayList<DivisionModel> divisionModels;
    TextInputEditText startAgeET,endAgeET,topicET;
    Button addDivBtn;
    LoadToast loadToast;
    int i = 1;

    String token="";
    String competitionID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        divisionBinding = ActivityEditDivisionBinding.inflate(getLayoutInflater());
        setContentView(divisionBinding.getRoot());

        startAgeET = findViewById(R.id.edit_start_age_field);
        endAgeET = findViewById(R.id.edit_end_age_field);
        topicET = findViewById(R.id.edit_topic_field);
        addDivBtn = findViewById(R.id.add_division_btn);



        competitionID = getIntent().getStringExtra("competition_id");
        token = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).getString("token","noToken");

        addDivBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadToast = new LoadToast(view.getContext());
                loadToast.setText("Adding Division");
                loadToast.setTranslationY(100);
                loadToast.setBorderColor(R.color.color_accent);
                loadToast.show();

                String name = topicET.getText().toString().trim();
                String startAge = startAgeET.getText().toString().trim();
                String endAge = endAgeET.getText().toString().trim();

                if (name.isEmpty()){
                    topicET.setError("Enter Topic");
                    topicET.requestFocus();
                }
                if (startAge.isEmpty()){
                    startAgeET.setError("Enter Age");
                    startAgeET.requestFocus();
                }
                if (endAge.isEmpty()){
                    endAgeET.setError("Enter Topic");
                    endAgeET.requestFocus();
                }

                if (topicET.getError()==null && startAgeET.getError()==null && endAgeET.getError()==null)
                {
                    Log.i("TAG", "onClick: >>>>>>>>>>>>>>>>>>> " + token);
                    Log.i("TAG", "onClick: >>>>>>>>>>>>>>>>>>> " + competitionID);

                    //api call to add age group
                    Call<AddAgeGroupAPICall> call = RetrofitClient.getClient().AddAgeGroup(token,competitionID,startAge,endAge,name);
                    call.enqueue(new Callback<AddAgeGroupAPICall>() {
                        @Override
                        public void onResponse(Call<AddAgeGroupAPICall> call, Response<AddAgeGroupAPICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                            if(response.body()!=null)
                            {
                                if (response.body().getSuccess()){
//                                    Toast.makeText(EditDivisionActivity.this,"Age group added successfully",Toast.LENGTH_SHORT).show();
                                    loadToast.success();
                                    divisionBinding.divisionNumber.setText(String.format("Division %s",++i));
                                    startAgeET.setText("");
                                    endAgeET.setText("");
                                    topicET.setText("");

                                }
                                else
                                    loadToast.error();
//                                    Toast.makeText(EditDivisionActivity.this,"Response Error",Toast.LENGTH_LONG).show();

                            }else
                                loadToast.error();
//                                Toast.makeText(EditDivisionActivity.this,"Response Error",Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onFailure(Call<AddAgeGroupAPICall> call, Throwable t) {
                            Toast.makeText(EditDivisionActivity.this,"Server Error",Toast.LENGTH_LONG).show();
                            loadToast.error();
                        }
                    });
                }

            }
        });

        setupRV();


        divisionBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupRV() {
        divisionModels = new ArrayList<>();
        addData();
        editDivisionsRVAdapter = new EditDivisionsRVAdapter(divisionModels,EditDivisionActivity.this,0);
        divisionBinding.editDivisionsRv.setLayoutManager(new LinearLayoutManager(EditDivisionActivity.this,LinearLayoutManager.VERTICAL,false));
        divisionBinding.editDivisionsRv.setAdapter(editDivisionsRVAdapter);
        editDivisionsRVAdapter.notifyDataSetChanged();
    }

    private void addData() {
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
                        for (DivisionModel divisionModel: competitionModel.getAgeGroups())
                            divisionModels.add(new DivisionModel(divisionModel.getStartAge(),divisionModel.getEndAge(),divisionModel.getDivisionTopic(),divisionModel.getDivisionID(),competitionID));
                        editDivisionsRVAdapter.notifyDataSetChanged();

                    }
                    else
                        Toast.makeText(EditDivisionActivity.this,"Response Error",Toast.LENGTH_LONG).show();

                }else
                    Toast.makeText(EditDivisionActivity.this,"Response Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<GetAgeGroupsAPICall> call, Throwable t) {
                Toast.makeText(EditDivisionActivity.this,"Server Error2",Toast.LENGTH_LONG).show();
            }
        });
    }

}