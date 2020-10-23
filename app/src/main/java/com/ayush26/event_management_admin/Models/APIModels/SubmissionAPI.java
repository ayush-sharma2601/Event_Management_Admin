package com.ayush26.event_management_admin.Models.APIModels;

import com.ayush26.event_management_admin.Models.SubmissionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmissionAPI {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("submissions")
    @Expose
    private SubmissionModel[] submissions;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public SubmissionModel[] getSubmissions() {
        return submissions;
    }

    public void setSubmissions(SubmissionModel[] submissions) {
        this.submissions = submissions;
    }
}
