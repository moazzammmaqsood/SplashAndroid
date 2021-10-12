package com.nouveau.splash.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static void Message(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static String getDatetoString(Date date){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);
        return  strDate;

    }

    public  static String getToken(String token){
        return "Bearer="+token;
    }


    public static String getDatetoStringformatted(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        String strDate = dateFormat.format(date);
        return  strDate;
    }
}
