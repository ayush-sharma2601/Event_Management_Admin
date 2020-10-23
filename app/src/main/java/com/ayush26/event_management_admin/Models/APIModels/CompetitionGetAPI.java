package com.ayush26.event_management_admin.Models.APIModels;

import com.ayush26.event_management_admin.Models.CompetitionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionGetAPI {

    @SerializedName("competitions")
    @Expose
    CompetitionModel[] competitionModels;

    @SerializedName("success")
    @Expose
    Boolean success;

    public CompetitionModel[] getCompetitionModels() {
        return competitionModels;
    }

    public void setCompetitionModels(CompetitionModel[] competitionModels) {
        this.competitionModels = competitionModels;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
