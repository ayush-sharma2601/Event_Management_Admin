package com.ayush26.event_management_admin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompetitionModel {
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

    @SerializedName("registration_fee")
    @Expose
    private int fee;

    @SerializedName("genre")
    @Expose
    private String genre;


    public CompetitionModel(String id, String title, String instructions, String startDate, String endDate, String genre, String[] winnerRewards, int fee, String genre1) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.genre = genre;
        this.winnerRewards = winnerRewards;
        this.fee = fee;
        this.genre = genre1;
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


