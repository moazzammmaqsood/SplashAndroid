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

    public SummaryDelivery() {
    }

    public SummaryDelivery(int clientid, int userid, String name, String address, int bottlesdelivered, int bottlesrecieved, int payment, String oncall, String date) {
        this.clientid = clientid;
        this.userid = userid;
        this.name = name;
        this.address = address;
        this.bottlesdelivered = bottlesdelivered;
        this.bottlesrecieved = bottlesrecieved;
        this.payment = payment;
        this.oncall = oncall;
        this.date = date;
    }

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

    public int getBottlesdelivered() {
        return bottlesdelivered;
    }

    public void setBottlesdelivered(int bottlesdelivered) {
        this.bottlesdelivered = bottlesdelivered;
    }

    public int getBottlesrecieved() {
        return bottlesrecieved;
    }

    public void setBottlesrecieved(int bottlesrecieved) {
        this.bottlesrecieved = bottlesrecieved;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getOncall() {
        return oncall;
    }

    public void setOncall(String oncall) {
        this.oncall = oncall;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
