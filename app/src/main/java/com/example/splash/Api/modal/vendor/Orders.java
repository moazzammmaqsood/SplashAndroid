package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Orders {
    @SerializedName("orderid")
    private int orderid ;

    @SerializedName("clientid")
    private int clientid ;

    @SerializedName("vendorid")
    private int vendorid ;

    @SerializedName("bottlesdelivered")
    private int bottlesdelivered ;

    @SerializedName("bottlesrecieved")
    private int bottlesrecieved ;

    @SerializedName("payment")
    private int payment;

    @SerializedName("date")
    private Date date;

    @SerializedName("status")
    private String status;

}
