package com.example.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.splash.Api.modal.SplashUser;
import com.example.splash.ui.login.LoginActivity;
import com.example.splash.utils.SessionManagement;
import com.example.splash.vendor.SigninActivity;
import com.example.splash.vendor.VenderDashboard;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final SplashUser user= SessionManagement.getSessionManagement(this).GetUser();


        final Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3*1000);

                    // After 5 seconds redirect to another intent
                    Intent i;
                    if(user==null) {
                       i = new Intent(getBaseContext(), SigninActivity.class);

                    }else{
                         i = new Intent(getBaseContext(), VenderDashboard.class);

                     }
                    startActivity(i);
                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }

}
