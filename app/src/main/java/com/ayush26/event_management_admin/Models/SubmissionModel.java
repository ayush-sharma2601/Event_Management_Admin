package com.ayush26.event_management_admin.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmissionModel {

    @SerializedName("submission_id")
    @Expose
    private String submissionID;

    @SerializedName("url")
    @Expose
    private String photoURL;

    public SubmissionModel(String submissionID, String photoURL) {
        this.submissionID = submissionID;
        this.photoURL = photoURL;
    }

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
