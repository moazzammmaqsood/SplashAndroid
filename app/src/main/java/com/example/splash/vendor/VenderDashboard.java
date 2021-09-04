package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splash.Api.interfaces.LoginApi;
import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.vendor.ClientDelivery;
import com.example.splash.R;
import com.example.splash.adapters.VendorDashboardAdapter;
import com.example.splash.data.model.ClientUserModel;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.Constants;
import com.example.splash.utils.SessionManagement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenderDashboard extends AppCompatActivity {

    private static final String TAG = "VENDORDASHBOARD";
    TextView vendorname ;
    TextView date;
    TextView Deliverybutton;
    TextView Requestedbutton;
    RecyclerView  recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        vendorname =  findViewById(R.id.vendorname);
        date= findViewById(R.id.date);
        Deliverybutton = findViewById(R.id.deliverybtn);
        Requestedbutton = findViewById(R.id.requestedbtn);
        recyclerView =findViewById(R.id.dashboardrev);
        activity=this;
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();

        if(user==null){
            Log.e(TAG, "onCreate: user null" );
        }


        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        String token = Constants.AUTHORIZATIONPREFIX+user.getToken();
        Call<List<ClientDelivery>> vendor= vendorApi.v1getdeliveries(token);
        vendor.enqueue(new Callback<List<ClientDelivery>>() {
            @Override
            public void onResponse(Call<List<ClientDelivery>> call, Response<List<ClientDelivery>> response) {
                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
        adapter = new VendorDashboardAdapter(response.body(),activity);
        recyclerView.setAdapter(adapter);
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );

                }
            }

            @Override
            public void onFailure(Call<List<ClientDelivery>> call, Throwable t) {

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboardmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.payments) {
            Intent i=new Intent(this,Payments.class);
            startActivity(i);

        } else if(id == R.id.earnings) {
            Intent i=new Intent(this,Payments.class);
            startActivity(i);

        } else if(id == R.id.addclient) {
            Intent i=new Intent(this,AddClient.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}
