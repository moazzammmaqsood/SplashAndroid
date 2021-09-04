package com.example.splash.Api.modal;

import com.google.gson.annotations.SerializedName;

public class SuccessResponse {
    @SerializedName("successMessage")
    String successMessage;


    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
