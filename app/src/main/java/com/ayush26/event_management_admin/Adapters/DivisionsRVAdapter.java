package com.ayush26.event_management_admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush26.event_management_admin.Activities.DivisionsCreation;
import com.ayush26.event_management_admin.Models.DivisionRVModel;
import com.ayush26.event_management_admin.Models.EventRVItem;
import com.ayush26.event_management_admin.R;

import java.util.ArrayList;

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
        return new DivisionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_rv_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionVH holder, int position) {
        holder.PopulateEvents(divisionRVModels.get(position),position);
    }

    @Override
    public int getItemCount() {
        return divisionRVModels.size();
    }

    public class DivisionVH extends RecyclerView.ViewHolder {
        TextView divName;
        public DivisionVH(@NonNull final View itemView) {
            super(itemView);

            divName = itemView.findViewById(R.id.division_name);


        }

        public void PopulateEvents(DivisionRVModel divisionRVModel, final int position) {
            divName.setText(divisionRVModel.getDivisionName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open division edit page
                    itemView.getContext().startActivity(new Intent(context, DivisionsCreation.class).putExtra("division_num",position));
                }
            });
        }
    }
}
