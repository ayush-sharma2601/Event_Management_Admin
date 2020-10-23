package com.ayush26.event_management_admin.Models.APIModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAgeGroupAPICall {

    @SerializedName("message")
    @Expose
    String msg;

    @SerializedName("success")
    @Expose
    Boolean success;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
