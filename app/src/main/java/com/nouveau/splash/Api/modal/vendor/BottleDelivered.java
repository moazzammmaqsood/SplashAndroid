package com.nouveau.splash.Api.modal.vendor;

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


    public BottleDelivered(int clientid, int bottlesdel, int bottlesrec, int payment, String date) {
        this.clientid = clientid;
        this.bottlesdel = bottlesdel;
        this.bottlesrec = bottlesrec;
        this.payment = payment;
        this.date = date;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getBottlesdel() {
        return bottlesdel;
    }

    public void setBottlesdel(int bottlesdel) {
        this.bottlesdel = bottlesdel;
    }

    public int getBottlesrec() {
        return bottlesrec;
    }

    public void setBottlesrec(int bottlesrec) {
        this.bottlesrec = bottlesrec;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
