package com.example.splash.Api.modal;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginResponse {

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("userid")
    @Expose
    public Integer userid;

    @SerializedName("name")
    @Expose
    public String name ;

    @SerializedName("email")
    @Expose
    public String email ;

    @SerializedName("password")
    @Expose
    public String password ;

    @SerializedName("username")
    @Expose
    public String  username;

    @SerializedName("phone")
    @Expose
    public String phone ;

    @SerializedName("userrole")
    @Expose
    public String userrole ;

    @SerializedName("status")
    @Expose
    public String status ;

    @SerializedName("createdby")
    @Expose
    public String createdby ;

    @SerializedName("createdon")
    @Expose
    public Date createdon;

    @SerializedName("vendorname")
    @Expose
    public String vendorname;

    public LoginResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }
}
