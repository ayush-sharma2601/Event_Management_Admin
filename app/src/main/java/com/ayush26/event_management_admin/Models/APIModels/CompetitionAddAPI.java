package com.ayush26.event_management_admin.Models.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionAddAPI {

    @SerializedName("competition")
    @Expose
    CompetitionAPIModel competitionAPIModel;

    @SerializedName("success")
    @Expose
    Boolean success;

    public CompetitionAPIModel getCompetitionAPIModel() {
        return competitionAPIModel;
    }

    public void setCompetitionAPIModel(CompetitionAPIModel competitionAPIModel) {
        this.competitionAPIModel = competitionAPIModel;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
