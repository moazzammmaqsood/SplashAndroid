package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class EditClientRequest {

    @SerializedName("userid")
    int userid ;

    @SerializedName("clientid")
    int clientid;

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

    @SerializedName("noofbottles")
    private int noofbottles;

    @SerializedName("oncall")
    private String oncall;

    public EditClientRequest() {
    }


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

    public EditClientRequest(int userid, int clientid, String name, String contactno, String address, String email, int frequency, int rate, int deposit, int noofbottles, String oncall) {
        this.userid = userid;
        this.clientid = clientid;
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
