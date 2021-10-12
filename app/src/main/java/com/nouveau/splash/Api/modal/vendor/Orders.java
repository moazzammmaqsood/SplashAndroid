package com.nouveau.splash.Api.modal.vendor;

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

    @SerializedName("rate")
    private Integer rate;



    public Integer getRate() {

        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getVendorid() {
        return vendorid;
    }

    public void setVendorid(int vendorid) {
        this.vendorid = vendorid;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
