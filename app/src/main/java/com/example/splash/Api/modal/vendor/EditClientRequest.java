package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class EditClientRequest {

    @SerializedName("userid")
    int userid ;

    @SerializedName("clientid")
    int clientid;

    @SerializedName("name")
    private String name;

    @SerializedName("contactno")
    private String contactno;

    @SerializedName("address")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("frequency")
    private int frequency;

    @SerializedName("rate")
    private int rate;

    @SerializedName("deposit")
    private int deposit;

    @SerializedName("noofbottles")
    private int noofbottles;

    @SerializedName("oncall")
    private String oncall;

}
