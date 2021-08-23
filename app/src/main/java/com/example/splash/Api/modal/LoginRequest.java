package com.example.splash.Api.modal;

import android.widget.EditText;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("username")
      private String username;
    @SerializedName("password")
      private String password;


    public LoginRequest(String username, String password) {
        this.username=username;
        this.password=password;
    }
}
