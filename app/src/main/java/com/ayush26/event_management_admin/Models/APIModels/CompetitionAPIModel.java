package com.ayush26.event_management_admin.Models.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionAPIModel {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("registration_fee")
    @Expose
    String fee;

    @SerializedName("start_time")
    @Expose
    String startTime;

    @SerializedName("end_time")
    @Expose
    String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
