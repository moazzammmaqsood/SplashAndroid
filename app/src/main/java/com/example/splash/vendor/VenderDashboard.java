package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.splash.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenderDashboard extends AppCompatActivity {

    private static final String TAG = "VENDORDASHBOARD";
    TextView vendorname ;
    TextView date;
    TextView Deliverybutton;
    TextView Requestedbutton;
    RecyclerView  recyclerView,recyclerViewoncall;
    private RecyclerView.LayoutManager layoutManager,linearManageroncall;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.Adapter adapteroncall;
    Activity activity;
    ProgressBar progressbar;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(myToolbar);
        vendorname =  findViewById(R.id.vendorname);
        date= findViewById(R.id.date);
        Deliverybutton = findViewById(R.id.deliverybtn);
        Requestedbutton = findViewById(R.id.oncallbtn);
        recyclerView =findViewById(R.id.dashboardrev);
        recyclerViewoncall=findViewById(R.id.recyclerViewoncall);
        progressbar=findViewById(R.id.progressbar);
        activity=this;
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();

        if(user==null){
            Log.e(TAG, "onCreate: user null" );
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }

        vendorname.setText(user.getVendorname());
        date.setText(Utils.getDatetoStringformatted(new Date()));
        Deliverybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showonDaily();
            }
        });

        Requestedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOnCall();
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerViewoncall.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        linearManageroncall=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewoncall.setLayoutManager(linearManageroncall);


        String token = Utils.getToken(user.getToken());
        Call<List<ClientDelivery>> vendor= vendorApi.v1getdeliveries(token);
        showProgress();
        vendor.enqueue(new Callback<List<ClientDelivery>>() {
            @Override
            public void onResponse(Call<List<ClientDelivery>> call, Response<List<ClientDelivery>> response) {
                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        List<ClientDelivery> dailydelivery=new ArrayList<>();
                        List<ClientDelivery> oncalldelivery= new ArrayList<>();

                        List<ClientDelivery> list= response.body();
                        for (ClientDelivery clientDelivery:
                             list ) {
                            if (clientDelivery.getOncall().equals("Y")){
                                oncalldelivery.add(clientDelivery);
                            }else{
                                dailydelivery.add(clientDelivery);
                            }
                        }

                        adapter = new VendorDashboardAdapter(dailydelivery,activity);
                        adapteroncall=new VendorDashboardAdapter(oncalldelivery,activity);
                        recyclerView.setAdapter(adapter);
                        recyclerViewoncall.setAdapter(adapteroncall);
                        hideProgress();
                        break;
                    case 401 :
                        Utils.Message(response.message(),activity);
                        SessionManagement.getSessionManagement(activity).logoutUser(activity);
                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Utils.Message(jObjError.getString("message"),VenderDashboard.this);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                        hideProgress();
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );

                }
            }

            @Override
            public void onFailure(Call<List<ClientDelivery>> call, Throwable t) {
                Utils.Message(t.getMessage(),getApplicationContext());
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
        if(id == R.id.logout) {
            SessionManagement.getSessionManagement(activity).logoutUser(activity);


        } else if(id == R.id.allclients) {
            Intent i=new Intent(this,ClientActivity.class);
            startActivity(i);

        } else if(id == R.id.addclient) {
            Intent i=new Intent(this,AddClient.class);
            startActivity(i);
        }
        else if(id == R.id.summaary) {
            Intent i=new Intent(this,SummaryActivity.class);
            startActivity(i);
        }
        else if(id== R.id.disabledUser){
            Intent i=new Intent(this,DisabledClients.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOnCall(){
        Deliverybutton.setTextColor(getResources().getColor(R.color.colorBlack));
        Requestedbutton.setTextColor(getResources().getColor(R.color.colorPrimary));

        recyclerView.setVisibility(View.GONE);
        recyclerViewoncall.setVisibility(View.VISIBLE);
    }

    private void showonDaily(){
        Deliverybutton.setTextColor(getResources().getColor(R.color.colorPrimary));
        Requestedbutton.setTextColor(getResources().getColor(R.color.colorBlack));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewoncall.setVisibility(View.GONE);
    }
    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}
