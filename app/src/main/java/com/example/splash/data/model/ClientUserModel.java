package com.example.splash.data.model;

public class ClientUserModel {



    private int userid;

    private String name ;

    private String address;

    private int rate ;

    private int bottlesrequired;

    private int frequency;

    public ClientUserModel(int userid, String name, String address, int rate, int bottlesrequired, int frequency) {
        this.userid = userid;
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.bottlesrequired = bottlesrequired;
        this.frequency = frequency;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getBottlesrequired() {
        return bottlesrequired;
    }

    public void setBottlesrequired(int bottlesrequired) {
        this.bottlesrequired = bottlesrequired;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rate=" + rate +
                ", bottlesrequired=" + bottlesrequired +
                ", frequency=" + frequency +
                '}';
    }
}
