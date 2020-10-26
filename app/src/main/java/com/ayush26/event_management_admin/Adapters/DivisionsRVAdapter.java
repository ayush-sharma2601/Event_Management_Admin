package com.ayush26.event_management_admin.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush26.event_management_admin.Activities.AgeGroupEntriesGrid;
import com.ayush26.event_management_admin.Activities.DecideWinner;
import com.ayush26.event_management_admin.Activities.DivisionsCreation;
import com.ayush26.event_management_admin.Activities.EventOnClick;
import com.ayush26.event_management_admin.Models.APIModels.AgeGroupCodeAPI;
import com.ayush26.event_management_admin.Models.APIModels.GetAgeGroupsAPICall;
import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.DivisionModel;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.Models.EventRVItem;
import com.ayush26.event_management_admin.R;
import com.ayush26.event_management_admin.RetrofitClient;
import com.ayush26.event_management_admin.databinding.ActivityAgeGroupEntriesGridBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisionsRVAdapter extends RecyclerView.Adapter<DivisionsRVAdapter.DivisionVH> {

    ArrayList<DivisionRVModel> divisionRVModels;
    Context context;
    int code;

    public DivisionsRVAdapter(ArrayList<DivisionRVModel> divisionRVModels, Context context, int code) {
        this.divisionRVModels = divisionRVModels;
        this.context = context;
        this.code = code;
    }

    @NonNull
    @Override
    public DivisionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DivisionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.division_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionVH holder, int position) {
        holder.PopulateEvents(divisionRVModels.get(position),position);
    }

    @Override
    public int getItemCount() {
        return divisionRVModels.size();
    }

    public void addData(DivisionRVModel divisionRVModel){
        divisionRVModels.add(divisionRVModel);
        notifyDataSetChanged();
    }

    public class DivisionVH extends RecyclerView.ViewHolder {
        TextView divName;
        MaterialTextView regNum;
        public DivisionVH(@NonNull final View itemView) {
            super(itemView);

            divName = itemView.findViewById(R.id.division_name);
            regNum = itemView.findViewById(R.id.participants_number);

        }

        public void PopulateEvents(final DivisionRVModel divisionRVModel, final int position) {
            divName.setText(divisionRVModel.getDivisionName());
            regNum.setText(divisionRVModel.getParticipantNumber());

            if (code==2) {
                regNum.setVisibility(View.INVISIBLE);
                //onclick open entries page
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), AgeGroupEntriesGrid.class)
                        .putExtra("age_group_id",divisionRVModel.getAge_group_id())
                                .putExtra("group_competition_id",divisionRVModel.getCompetition_id()));
                    }
                });
            }
            else if (code==4){
                regNum.setVisibility(View.INVISIBLE);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemView.getContext().startActivity(new Intent(itemView.getContext(), DecideWinner.class)
                                .putExtra("age_group_id",divisionRVModel.getAge_group_id())
                                .putExtra("group_competition_id",divisionRVModel.getCompetition_id()));
                    }
                });
            }
            else {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        final String token = itemView.getContext().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE).getString("token","noToken");
                        Log.i("TAG", "onLongClick: >>>>>>>>> token: "+token );
                        Call<AgeGroupCodeAPI> call1 = RetrofitClient.getClient().getAgeGroupCode(token,divisionRVModel.getAge_group_id());
                        call1.enqueue(new Callback<AgeGroupCodeAPI>() {
                            @Override
                            public void onResponse(Call<AgeGroupCodeAPI> call1, final Response<AgeGroupCodeAPI> response) {
//                Toast.makeText(getContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                                if(response.body()!=null)
                                {
                                    if (response.body().getSuccess()){
//                                        final Snackbar snackbar = Snackbar.make(itemView.getRootView(),String.format("Judge code: %s",response.body().getCode()),Snackbar.LENGTH_INDEFINITE);
//                                        snackbar.setAction("OK", new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View view) {
//                                                snackbar.dismiss();
//                                            }
//                                        });
//                                        snackbar.show();
                                        MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(itemView.getContext());
                                        builder.setTitle("Judge Code")
                                                .setMessage(String.format("Code for judge login: %s",response.body().getCode()))
                                                .setPositiveButton("share", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        Intent sendIntent = new Intent();
                                                        sendIntent.setAction(Intent.ACTION_SEND);
                                                        sendIntent.putExtra(Intent.EXTRA_TEXT, response.body().getCode());
                                                        sendIntent.setType("text/plain");

                                                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                                                        itemView.getContext().startActivity(shareIntent);
                                                    }
                                                })
                                                .setNeutralButton("copy", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        ClipboardManager clipboardManager=(ClipboardManager)itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                                        ClipData clipData=ClipData.newPlainText("code",String.format("Code for Judge Login: %s",response.body().getCode()));
                                                        clipboardManager.setPrimaryClip(clipData);
                                                        Toast.makeText(itemView.getContext(),"Code has been copied",Toast.LENGTH_SHORT).show();
                                                    }
                                                }).show();


                                    }
                                    else
                                        Toast.makeText(itemView.getContext(),"Response Error",Toast.LENGTH_LONG).show();

                                }else
                                    Toast.makeText(itemView.getContext(),"Response Error",Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<AgeGroupCodeAPI> call, Throwable t) {
                                Toast.makeText(itemView.getContext(),"Server Error2",Toast.LENGTH_LONG).show();
                            }
                        });
                        return false;
                    }
                });
            }


        }
    }
}
