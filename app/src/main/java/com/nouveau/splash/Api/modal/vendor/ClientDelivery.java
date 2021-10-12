package com.nouveau.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class ClientDelivery {

    @SerializedName("clientid")
    int clientid;

    @SerializedName("userid")
    int userid;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("bottles")
    int bottles;

    @SerializedName("frequency")
    int frequency;

    @SerializedName("rate")
    int rate;

    @SerializedName("oncall")
    String oncall;


   @SerializedName("days")
    int days;


    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getOncall() {
        return oncall;
    }

    public void setOncall(String oncall) {
        this.oncall = oncall;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
