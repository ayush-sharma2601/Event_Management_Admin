package com.ayush26.event_management_admin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgeGroupCompetitionModel {
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

    @SerializedName("total_collection")
    @Expose
    private int totalCollection;

    @SerializedName("end_time")
    @Expose
    private String endDate;

    @SerializedName("age_groups")
    @Expose
    private DivisionModel[] ageGroups;

    public int getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(int totalCollection) {
        this.totalCollection = totalCollection;
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

    public DivisionModel[] getAgeGroups() {
        return ageGroups;
    }

    public void setAgeGroups(DivisionModel[] ageGroups) {
        this.ageGroups = ageGroups;
    }

    public AgeGroupCompetitionModel(String id, String title, String instructions, String startDate, String endDate, DivisionModel[] ageGroups) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ageGroups = ageGroups;
    }
}


