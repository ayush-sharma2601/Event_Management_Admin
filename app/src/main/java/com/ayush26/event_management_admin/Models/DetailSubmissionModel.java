package com.ayush26.event_management_admin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailSubmissionModel {

    @SerializedName("submission_id")
    @Expose
    private String submissionID;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("participant")
    @Expose
    private ParticipantModel participant;

    @SerializedName("age_group")
    @Expose
    private AgeGroupCompetitionModel ageGroup;

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ParticipantModel getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantModel participant) {
        this.participant = participant;
    }

    public AgeGroupCompetitionModel getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroupCompetitionModel ageGroup) {
        this.ageGroup = ageGroup;
    }
}
