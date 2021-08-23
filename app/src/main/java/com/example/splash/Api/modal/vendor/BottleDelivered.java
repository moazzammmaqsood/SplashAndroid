package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class BottleDelivered {

    @SerializedName("clientid")
    int clientid;

    @SerializedName("bottlesdel")
    int bottlesdel;

    @SerializedName("bottlesrec")
    int bottlesrec;

    @SerializedName("payment")
    int payment;

    @SerializedName("date")
    String date;


}
