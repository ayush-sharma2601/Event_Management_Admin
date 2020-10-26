package com.ayush26.event_management_admin.Models;

import android.os.Parcelable;

import com.ayush26.event_management_admin.Models.APIModels.ResultModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

public class DivisionModel {

    @SerializedName("start_age")
    @Expose
    private int startAge;

    @SerializedName("end_age")
    @Expose
    private int endAge;

    @SerializedName("name")
    @Expose
    private String divisionTopic;

    @SerializedName("age_group_id")
    @Expose
    private String divisionID;

    @SerializedName("competition_id")
    @Expose
    private String competition_id;

    @SerializedName("participants")
    @Expose
    private ParticipantModel[] participantNum;

    @SerializedName("winner")
    @Expose
    private ResultModel winners;
;

    public DivisionModel(int startAge, int endAge, String divisionTopic, String divisionID, String competition_id) {
        this.startAge = startAge;
        this.endAge = endAge;
        this.divisionTopic = divisionTopic;
        this.divisionID = divisionID;
        this.competition_id = competition_id;
    }
    public DivisionModel(int startAge, int endAge, String divisionTopic, String divisionID, String competition_id,ResultModel winners) {
        this.startAge = startAge;
        this.endAge = endAge;
        this.divisionTopic = divisionTopic;
        this.divisionID = divisionID;
        this.competition_id = competition_id;
        this.winners =winners;
    }


    public ResultModel getWinners() {
        return winners;
    }

    public void setWinners(ResultModel winners) {
        this.winners = winners;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public ParticipantModel[] getParticipantNum() {
        return participantNum;
    }

    public void setParticipantNum(ParticipantModel[] participantNum) {
        this.participantNum = participantNum;
    }

    public String getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(String divisionID) {
        this.divisionID = divisionID;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public String getDivisionTopic() {
        return divisionTopic;
    }

    public void setDivisionTopic(String divisionTopic) {
        this.divisionTopic = divisionTopic;
    }

}
