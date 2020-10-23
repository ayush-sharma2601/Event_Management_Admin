package com.ayush26.event_management_admin.Models.APIModels;

import com.ayush26.event_management_admin.Models.DetailSubmissionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmissionDetailAPICall {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("submission")
    @Expose
    private DetailSubmissionModel detailSubmissionModel;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DetailSubmissionModel getDetailSubmissionModel() {
        return detailSubmissionModel;
    }

    public void setDetailSubmissionModel(DetailSubmissionModel detailSubmissionModel) {
        this.detailSubmissionModel = detailSubmissionModel;
    }
}
