package com.example.splash.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Display;

import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.vendor.UserClient;
import com.example.splash.vendor.SigninActivity;
import com.google.gson.Gson;

public class SessionManagement {

    private static final String MYPREFS = "MyPrefs";
    private static final String USERKEY = "user";

    static SharedPreferences sharedPreferences;
    static SessionManagement sessionManagement;


    private SessionManagement(){

    }

    public static SessionManagement getSessionManagement(Activity activity){
        if(sessionManagement==null){
            sessionManagement=new SessionManagement();
        }
        if(sharedPreferences==null) {

            sharedPreferences = activity.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        }
        return sessionManagement;
    }
    public static SharedPreferences getSharedPreferences(Activity activity){

        if(sharedPreferences==null) {

            sharedPreferences = activity.getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    public void SetUser(SplashUser user){
        Gson gson=new Gson();
       String jsonuser= gson.toJson(user);
       sharedPreferences.edit().putString(USERKEY,jsonuser).commit();


    }
    public SplashUser GetUser( ){
        Gson gson=new Gson();
       String jsonuser= sharedPreferences.getString(USERKEY,"");
        SplashUser user;
       if(jsonuser==null || jsonuser.isEmpty()){
           user=null;
       }else {
            user = gson.fromJson(jsonuser, SplashUser.class);
       }

       return user;


    }

    public void logoutUser(Activity activity){
        sharedPreferences.edit().clear().commit();
        Intent intent=new Intent(activity, SigninActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

}
