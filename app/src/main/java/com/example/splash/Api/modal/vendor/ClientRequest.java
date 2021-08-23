package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class ClientRequest {

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

    @SerializedName("lastdelivery")
    private String lastdelivery;

    @SerializedName("lastbottles")
    int lastbottles;

    @SerializedName("lastrecieved")
    int lastrecieved;

    @SerializedName("lastpayment")
    int lastpayment;

    @SerializedName("noofbottles")
    private int noofbottles;

    @SerializedName("oncall")
    private String oncall;

}
