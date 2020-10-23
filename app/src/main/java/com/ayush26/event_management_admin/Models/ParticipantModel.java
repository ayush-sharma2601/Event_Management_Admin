package com.ayush26.event_management_admin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantModel {
    @SerializedName("participant_id")
    @Expose
    String participantID;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("age")
    @Expose
    int age;

    @SerializedName("school_name")
    @Expose
    String school;

    public ParticipantModel(String participantID, String name, int age, String school) {
        this.participantID = participantID;
        this.name = name;
        this.age = age;
        this.school = school;
    }

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
