package com.ayush26.event_management_admin.Models.APIModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgeGroupCodeAPI {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("code")
    @Expose
    private String code;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
