package com.example.splash.Api.interfaces;

import com.example.splash.Api.modal.LoginRequest;
import com.example.splash.Api.modal.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/v1/public/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);



}
