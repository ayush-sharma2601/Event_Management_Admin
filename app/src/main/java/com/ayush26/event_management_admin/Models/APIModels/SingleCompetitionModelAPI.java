package com.ayush26.event_management_admin.Models.APIModels;

import android.widget.ScrollView;

import com.ayush26.event_management_admin.Models.DivisionModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleCompetitionModelAPI {
    @SerializedName("competition_id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String instructions;

    @SerializedName("start_time")
    @Expose
    private String startDate;

    @SerializedName("end_time")
    @Expose
    private String endDate;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("winner_rewards")
    @Expose
    private String[] winnerRewards;

    @SerializedName("age_groups")
    @Expose
    private DivisionModel[] ageGroups;

    @SerializedName("registration_fee")
    @Expose
    private int fee;

    @SerializedName("total_collection")
    @Expose
    private int totalCollection;

    @SerializedName("genre")
    @Expose
    private String genre;




    public SingleCompetitionModelAPI(String id, String title, String instructions, String startDate, String endDate,
                                     String state, String[] winnerRewards, DivisionModel[] ageGroups, int fee, int totalCollection,
                                     String genre) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.winnerRewards = winnerRewards;
        this.ageGroups = ageGroups;
        this.fee = fee;
        this.totalCollection = totalCollection;
        this.genre = genre;
    }



    public int getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(int totalCollection) {
        this.totalCollection = totalCollection;
    }

    public DivisionModel[] getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(DivisionModel[] ageGroups) {
        this.ageGroups = ageGroups;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String[] getWinnerRewards() {
        return winnerRewards;
    }

    public void setWinnerRewards(String[] winnerRewards) {
        this.winnerRewards = winnerRewards;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}


