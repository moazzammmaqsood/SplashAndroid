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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public String getLastdelivery() {
        return lastdelivery;
    }

    public void setLastdelivery(String lastdelivery) {
        this.lastdelivery = lastdelivery;
    }

    public int getLastbottles() {
        return lastbottles;
    }

    public void setLastbottles(int lastbottles) {
        this.lastbottles = lastbottles;
    }

    public int getLastrecieved() {
        return lastrecieved;
    }

    public void setLastrecieved(int lastrecieved) {
        this.lastrecieved = lastrecieved;
    }

    public int getLastpayment() {
        return lastpayment;
    }

    public void setLastpayment(int lastpayment) {
        this.lastpayment = lastpayment;
    }

    public int getNoofbottles() {
        return noofbottles;
    }

    public void setNoofbottles(int noofbottles) {
        this.noofbottles = noofbottles;
    }

    public String getOncall() {
        return oncall;
    }

    public void setOncall(String oncall) {
        this.oncall = oncall;
    }

    public ClientRequest(String name, String contactno, String address, String email, int frequency, int rate, int deposit, String lastdelivery, int lastbottles, int lastrecieved, int lastpayment, int noofbottles, String oncall) {
        this.name = name;
        this.contactno = contactno;
        this.address = address;
        this.email = email;
        this.frequency = frequency;
        this.rate = rate;
        this.deposit = deposit;
        this.lastdelivery = lastdelivery;
        this.lastbottles = lastbottles;
        this.lastrecieved = lastrecieved;
        this.lastpayment = lastpayment;
        this.noofbottles = noofbottles;
        this.oncall = oncall;
    }

    public ClientRequest(String name, String contactno, String address, String email, int frequency, int rate, int deposit,int noofbottles,String oncall) {
        this.name = name;
        this.contactno = contactno;
        this.address = address;
        this.email = email;
        this.frequency = frequency;
        this.rate = rate;
        this.deposit = deposit;
        this.noofbottles = noofbottles;
        this.oncall = oncall;
    }

}
