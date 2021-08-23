package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class ClientDetails {

    @SerializedName("userid")
    int userid;

    @SerializedName("clientid")
    int clientid;

    @SerializedName("name")
    String name;

    @SerializedName("contact")
    String contact;

    @SerializedName("address")
    String address;

    @SerializedName("totalbottles")
    int totalbottles;

    @SerializedName("bottlesholding")
    int bottlesholding;

    @SerializedName("rate")
    int rate;

    @SerializedName("lastdelivery")
    String  lastdelivery;

    @SerializedName("daysperdelivery")
    int daysperdelivery;

    @SerializedName("paymentremaining")
    int paymentremaining;

    @SerializedName("paid")
    int paid;

    @SerializedName("deposit")
    int deposit;

    @SerializedName("bottles")
    int bottles;

    @SerializedName("oncall")
    String oncall;
}
