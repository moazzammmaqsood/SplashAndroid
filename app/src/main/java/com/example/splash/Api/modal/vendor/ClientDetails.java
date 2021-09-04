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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalbottles() {
        return totalbottles;
    }

    public void setTotalbottles(int totalbottles) {
        this.totalbottles = totalbottles;
    }

    public int getBottlesholding() {
        return bottlesholding;
    }

    public void setBottlesholding(int bottlesholding) {
        this.bottlesholding = bottlesholding;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getLastdelivery() {
        return lastdelivery;
    }

    public void setLastdelivery(String lastdelivery) {
        this.lastdelivery = lastdelivery;
    }

    public int getDaysperdelivery() {
        return daysperdelivery;
    }

    public void setDaysperdelivery(int daysperdelivery) {
        this.daysperdelivery = daysperdelivery;
    }

    public int getPaymentremaining() {
        return paymentremaining;
    }

    public void setPaymentremaining(int paymentremaining) {
        this.paymentremaining = paymentremaining;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public String getOncall() {
        return oncall;
    }

    public void setOncall(String oncall) {
        this.oncall = oncall;
    }
}
