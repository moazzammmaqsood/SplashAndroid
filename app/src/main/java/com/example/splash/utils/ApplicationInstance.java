package com.example.splash.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationInstance {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    static ApplicationInstance instance;
    static Retrofit retrofit ;
    private ApplicationInstance(){}

    public static ApplicationInstance getInstance(){
        if(instance==null){
            instance=new ApplicationInstance();
        }
        return instance;
    }


    public Retrofit getRetrofitInstance(){
     if(retrofit==null){
         Gson gson = new GsonBuilder()
                 .setDateFormat(DATE_FORMAT)
                 .create();
      retrofit=  new Retrofit.Builder()
                 .baseUrl(Constants.BASE_URL)
              .addConverterFactory(GsonConverterFactory.create(gson))
                 .build();

     }

     return retrofit;


    }







}
