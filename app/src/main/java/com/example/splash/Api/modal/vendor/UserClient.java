package com.example.splash.Api.modal.vendor;

import com.google.gson.annotations.SerializedName;

public class UserClient
{

    @SerializedName("userid")
     int userid ;

    @SerializedName("clientid")
     int clientid;

    @SerializedName("name")
     String name;

    @SerializedName("address")
     String Address;

    @SerializedName("bottles")
     int bottles;

    @SerializedName("status")
    String status;

    public UserClient() {
    }

    public UserClient(int userid, int clientid, String name, String address, int bottles,String  status) {
        this.userid = userid;
        this.clientid = clientid;
        this.name = name;
        Address = address;
        this.bottles = bottles;
        this.status=status;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getBottles() {
        return bottles;
    }

    public void setBottles(int bottles) {
        this.bottles = bottles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserClient{" +
                "userid=" + userid +
                ", clientid=" + clientid +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                ", bottles=" + bottles +
                ", status='" + status + '\'' +
                '}';
    }
}
