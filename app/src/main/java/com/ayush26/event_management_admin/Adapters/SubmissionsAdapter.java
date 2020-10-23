package com.ayush26.event_management_admin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayush26.event_management_admin.Activities.SingleSubmissionPage;
import com.ayush26.event_management_admin.Models.SubmissionModel;
import com.ayush26.event_management_admin.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SubmissionsAdapter extends RecyclerView.Adapter<SubmissionsAdapter.SubmissionVH> {

    ArrayList<SubmissionModel> submissionModels;
    Context context;
    int code;

    public SubmissionsAdapter(ArrayList<SubmissionModel> submissionModels, Context context, int code) {
        this.submissionModels = submissionModels;
        this.context = context;
        this.code = code;
    }

    @NonNull
    @Override
    public SubmissionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SubmissionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.submission_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SubmissionVH holder, int position) {
        holder.populateSubmissions(submissionModels.get(position));
    }

    @Override
    public int getItemCount() {
        return submissionModels.size();
    }

    public class SubmissionVH extends RecyclerView.ViewHolder {
        ImageView image;
        public SubmissionVH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.submission_entry_image);

        }

        public void populateSubmissions(final SubmissionModel submissionModel) {
            Glide.with(itemView.getContext()).load(submissionModel.getPhotoURL()).apply(new RequestOptions().override(100,100)).into(image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), SingleSubmissionPage.class);
                    if (code==1){
                        intent.putExtra("purpose","judge");
                    }
                    else intent.putExtra("purpose","admin");
                    intent.putExtra("photo_url",submissionModel.getPhotoURL());
                    intent.putExtra("submission_id",submissionModel.getSubmissionID());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
