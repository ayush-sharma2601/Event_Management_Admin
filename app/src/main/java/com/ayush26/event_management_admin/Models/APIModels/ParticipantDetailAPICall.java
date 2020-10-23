package com.ayush26.event_management_admin.Models.APIModels;

import com.ayush26.event_management_admin.Models.ParticipantModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParticipantDetailAPICall {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("participant")
    @Expose
    private ParticipantModel participant;

    @SerializedName("contact")
    @Expose
    private ContactModelAPI contactDetails;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ParticipantModel getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantModel participant) {
        this.participant = participant;
    }

    public ContactModelAPI getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactModelAPI contactDetails) {
        this.contactDetails = contactDetails;
    }
}
