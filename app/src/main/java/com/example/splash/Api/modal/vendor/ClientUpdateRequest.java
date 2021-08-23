package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class ClientUpdateRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("contactno")
    private String contactno;
    @SerializedName("address")
    private String address;
    @SerializedName("daysdelivery")
    private int daysdelivery;
    @SerializedName("rate")
    private int rate;
    @SerializedName("deposit")
    private int deposit;
    @SerializedName("noofbottles")
    private int noofbottles;
    @SerializedName("oncall")
    private String oncall;
}
