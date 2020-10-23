package com.ayush26.event_management_admin.Models.APIModels;

import com.ayush26.event_management_admin.Models.AgeGroupCompetitionModel;
import com.ayush26.event_management_admin.Models.CompetitionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAgeGroupsAPICall {

    @SerializedName("competition")
    @Expose
    AgeGroupCompetitionModel ageGroupCompetitionModels;

    @SerializedName("success")
    @Expose
    Boolean success;

    public AgeGroupCompetitionModel getAgeGroupCompetitionModels() {
        return ageGroupCompetitionModels;
    }

    public void setAgeGroupCompetitionModels(AgeGroupCompetitionModel ageGroupCompetitionModels) {
        this.ageGroupCompetitionModels = ageGroupCompetitionModels;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
