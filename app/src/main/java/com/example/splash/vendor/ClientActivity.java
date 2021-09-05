package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.vendor.ClientDelivery;
import com.example.splash.Api.modal.vendor.UserClient;
import com.example.splash.R;
import com.example.splash.adapters.AllClientAdapter;
import com.example.splash.adapters.VendorDashboardAdapter;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends AppCompatActivity {


    private static final String TAG = "CLIENTACTIVITY";
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    Activity activity;
    private static RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        activity=this;
        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();

        if(user==null){
            Log.e(TAG, "onCreate: user null" );
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }

        
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String token= Utils.getToken(user.getToken());
        Call<List<UserClient>> vendor= vendorApi.v1getclients(token);
        vendor.enqueue(new Callback<List<UserClient>>() {
            @Override
            public void onResponse(Call<List<UserClient>> call, Response<List<UserClient>> response) {
                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        adapter = new AllClientAdapter(response.body(),activity);
                        recyclerView.setAdapter(adapter);
                        break;
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );

                }
            }

            @Override
            public void onFailure(Call<List<UserClient>> call, Throwable t) {

            }
        });




    }
}
