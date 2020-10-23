package com.ayush26.event_management_admin.Models;

public class DivisionRVModel {
    private String divisionName;
    private String participantNumber;
    private String competition_id;
    private String age_group_id;


    public DivisionRVModel(String divisionName, String participantNumber, String competition_id, String age_group_id) {
        this.divisionName = divisionName;
        this.participantNumber = participantNumber;
        this.competition_id = competition_id;
        this.age_group_id = age_group_id;
    }

    public String getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(String competition_id) {
        this.competition_id = competition_id;
    }

    public String getAge_group_id() {
        return age_group_id;
    }

    public void setAge_group_id(String age_group_id) {
        this.age_group_id = age_group_id;
    }

    public String getParticipantNumber() {
        return participantNumber;
    }

    public void setParticipantNumber(String participantNumber) {
        this.participantNumber = participantNumber;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
