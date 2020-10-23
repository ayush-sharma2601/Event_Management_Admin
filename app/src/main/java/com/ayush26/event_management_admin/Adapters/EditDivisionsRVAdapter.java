package com.ayush26.event_management_admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush26.event_management_admin.Activities.AgeGroupEntriesGrid;
import com.ayush26.event_management_admin.Models.APIModels.APICall;
import com.ayush26.event_management_admin.Models.APIModels.AgeGroupCodeAPI;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDivisionsRVAdapter extends RecyclerView.Adapter<EditDivisionsRVAdapter.DivisionVH> {

    ArrayList<DivisionModel> divisionRVModels;
    Context context;
    int code;

    public EditDivisionsRVAdapter(ArrayList<DivisionModel> divisionRVModels, Context context, int code) {
        this.divisionRVModels = divisionRVModels;
        this.context = context;
        this.code = code;
    }

    @NonNull
    @Override
    public DivisionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DivisionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_division_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionVH holder, int position) {
        holder.PopulateEvents(divisionRVModels.get(position),position);
    }

    @Override
    public int getItemCount() {
        return divisionRVModels.size();
    }

    public void addData(DivisionModel divisionRVModel){
        divisionRVModels.add(divisionRVModel);
        notifyDataSetChanged();
    }

    public class DivisionVH extends RecyclerView.ViewHolder {
        TextInputEditText startAgeET,endAgeET,nameET;
        Button updateBtn;
        MaterialTextView divNum;
        public DivisionVH(@NonNull final View itemView) {
            super(itemView);
            startAgeET = itemView.findViewById(R.id.edit_start_age_field);
            endAgeET = itemView.findViewById(R.id.edit_end_age_field);
            nameET = itemView.findViewById(R.id.edit_topic_field);
            updateBtn = itemView.findViewById(R.id.update_division_btn);
            divNum = itemView.findViewById(R.id.division_number);
        }

        public void PopulateEvents(final DivisionModel divisionRVModel, final int position) {
            startAgeET.setText(String.format("%s",divisionRVModel.getStartAge()));
            endAgeET.setText(String.format("%s",divisionRVModel.getEndAge()));
            nameET.setText(divisionRVModel.getDivisionTopic());
            divNum.setText(String.format("Division %s",position+1));
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final LoadToast loadToast ;
                    loadToast = new LoadToast(view.getContext());
                    loadToast.setText("Updating Division");
                    loadToast.setTranslationY(100);
                    loadToast.setBorderColor(R.color.color_accent);
                    loadToast.show();

                    String name = nameET.getText().toString().trim();
                    String startAge = startAgeET.getText().toString().trim();
                    String endAge = endAgeET.getText().toString().trim();

                    if (name.isEmpty()){
                        nameET.setError("Enter Topic");
                        nameET.requestFocus();
                    }
                    if (startAge.isEmpty()){
                        startAgeET.setError("Enter Age");
                        startAgeET.requestFocus();
                    }
                    if (endAge.isEmpty()){
                        endAgeET.setError("Enter Topic");
                        endAgeET.requestFocus();
                    }

                    if (nameET.getError()==null && startAgeET.getError()==null && endAgeET.getError()==null)
                    {
                        final String token = itemView.getContext().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE).getString("token","noToken");
                        Log.i("TAG", "onClick: >>>>>>>>> token: "+token );

                        Call<APICall> call1 = RetrofitClient.getClient().updateCompetitionAgeGroup(token,divisionRVModel.getCompetition_id(),divisionRVModel.getDivisionID(),
                                Integer.parseInt(startAge),Integer.parseInt(endAge),name);
                        call1.enqueue(new Callback<APICall>() {
                            @Override
                            public void onResponse(Call<APICall> call1, Response<APICall> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                                if(response.body()!=null)
                                {
                                    if (response.body().getSuccess()){
//                                        Toast.makeText(itemView.getContext(),"updated successfully",Toast.LENGTH_SHORT).show();
                                        loadToast.success();
                                    }
                                    else
                                        loadToast.error();
//                                        Toast.makeText(itemView.getContext(),"Response Error",Toast.LENGTH_LONG).show();

                                }else
                                    loadToast.error();
//                                    Toast.makeText(itemView.getContext(),"Response Error",Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<APICall> call, Throwable t) {
//                                Toast.makeText(itemView.getContext(),"Server Error2",Toast.LENGTH_LONG).show();
                                loadToast.error();
                            }
                        });
                    }
                }
            });

        }
    }
}
