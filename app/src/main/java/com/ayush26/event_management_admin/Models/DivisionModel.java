package com.ayush26.event_management_admin.Models;

public class DivisionModel {
    private String divisionName;
    private int startAge;
    private int endAge;
    private String divisionTopic;
    private String judgeEmail;

    public DivisionModel(String divisionName, int startAge, int endAge, String divisionTopic, String judgeEmail) {
        this.divisionName = divisionName;
        this.startAge = startAge;
        this.endAge = endAge;
        this.divisionTopic = divisionTopic;
        this.judgeEmail = judgeEmail;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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

    public String getJudgeEmail() {
        return judgeEmail;
    }

    public void setJudgeEmail(String judgeEmail) {
        this.judgeEmail = judgeEmail;
    }
}
