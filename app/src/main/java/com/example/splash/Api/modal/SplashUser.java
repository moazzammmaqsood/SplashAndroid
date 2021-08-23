package com.example.splash.Api.modal;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class SplashUser implements Serializable {

    private String token;
    private int userid;
    private String name ;
    private String email ;
    private String password ;
    private String  username;
    private String phone ;
    private String userrole ;
    private String status ;
    private String createdby ;
    private Date createdon;
    private String vendorname;

    public SplashUser(LoginResponse loginResponse){
        this.token = loginResponse.token;
        this.userid = loginResponse.userid;
        this.name = loginResponse.name;
        this.email = loginResponse.email;
        this.password = loginResponse.password;
        this.username = loginResponse.username;
        this.phone = loginResponse.phone;
        this.userrole = loginResponse.userrole;
        this.status = loginResponse.status;
        this.createdby = loginResponse.createdby;
        this.createdon = loginResponse.createdon;
        this.vendorname = loginResponse.vendorname;
    }

    public SplashUser(String token, int userid, String name, String email, String password, String username, String phone, String userrole, String status, String createdby, Date createdon, String vendorname) {
        this.token = token;
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.userrole = userrole;
        this.status = status;
        this.createdby = createdby;
        this.createdon = createdon;
        this.vendorname = vendorname;
    }

    public SplashUser() {
    }

    @Override
    public String toString() {
        return "SplashUser{" +
                "token='" + token + '\'' +
                ", userid=" + userid +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", userrole='" + userrole + '\'' +
                ", status='" + status + '\'' +
                ", createdby='" + createdby + '\'' +
                ", createdon=" + createdon +
                ", vendorname='" + vendorname + '\'' +
                '}';
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
