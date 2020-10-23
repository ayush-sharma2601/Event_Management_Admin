package com.ayush26.event_management_admin.Models.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleCompetitionAPICall {

    @SerializedName("competition")
    @Expose
    SingleCompetitionModelAPI competitionAPIModel;

    @SerializedName("success")
    @Expose
    Boolean success;

    public SingleCompetitionModelAPI getCompetitionAPIModel() {
        return competitionAPIModel;
    }

    public void setCompetitionAPIModel(SingleCompetitionModelAPI competitionAPIModel) {
        this.competitionAPIModel = competitionAPIModel;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
