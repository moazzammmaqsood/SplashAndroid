package com.nouveau.splash.Api.interfaces;

import com.nouveau.splash.Api.modal.LoginRequest;
import com.nouveau.splash.Api.modal.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("/api/v1/public/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @Headers("Content-Type:application/json")
    @GET("/api/v1/public/auth/contactus")
    Call<String> contactus();



}
