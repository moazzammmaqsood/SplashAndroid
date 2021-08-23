package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class SummaryDelivery {

    @SerializedName("clientid")
    int clientid;

    @SerializedName("userid")
    int userid;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("bottlesdelivered")
    int bottlesdelivered;

    @SerializedName("bottlesrecieved")
    int bottlesrecieved;

    @SerializedName("payment")
    int payment;

    @SerializedName("oncall")
    String oncall;


    @SerializedName("date")
    String date;


}
