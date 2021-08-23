package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.R;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;

public class ViewClient extends AppCompatActivity {


    LinearLayout deliverBtn,orderBtn,crossBtn;
    TextView clientname,clientcontact,clientaddress,clientbottlesdel,clientbottles,clientbottlesrate,clientlastdel,clientdaysdel,clientpaid,clientbalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);

        Intent intent=getIntent();
//        intent.getExtras();

        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            finish();
        }

        clientname =  findViewById(R.id.clientname);
        clientcontact=  findViewById(R.id.clientname);
        clientaddress=  findViewById(R.id.clientname);
        clientbottlesdel=  findViewById(R.id.clientname);
        clientbottles=  findViewById(R.id.clientname);
        clientbottlesrate=  findViewById(R.id.clientname);
        clientlastdel=  findViewById(R.id.clientname);
        clientdaysdel=  findViewById(R.id.clientname);
        clientpaid=  findViewById(R.id.clientname);
        clientbalance=  findViewById(R.id.clientname);
        deliverBtn= findViewById(R.id.deliverBtn);
        orderBtn=findViewById(R.id.orderBtn);
        crossBtn=findViewById(R.id.crossBtn);

        VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
//        Call<SuccessResponse> v1deliverclient= vendorApi.v1deliverclient()


        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
