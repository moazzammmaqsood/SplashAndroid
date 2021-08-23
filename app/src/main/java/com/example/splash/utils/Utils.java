package com.example.splash.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {


    public static void Message(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

}
