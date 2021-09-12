package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.vendor.UserClient;
import com.example.splash.R;
import com.example.splash.adapters.AllClientAdapter;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisabledClients extends AppCompatActivity {

    private static final String TAG = "DisabledClients";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AllClientAdapter adapter;
    Activity activity;
    ProgressBar progressBar;
    List<UserClient> list=new ArrayList<UserClient>();
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disabled_clients);
        recyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressbar);
        activity=this;
        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(user==null){
            Log.e(TAG, "onCreate: user null" );
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }
        String token= Utils.getToken(user.getToken());
        VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<List<UserClient>> vendor= vendorApi.v1getclients(token);
        vendor.enqueue(new Callback<List<UserClient>>() {
            @Override
            public void onResponse(Call<List<UserClient>> call, Response<List<UserClient>> response) {
                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        list=response.body();
                        List<UserClient> disableduser=new ArrayList<>();
                        if(!list.isEmpty()) {
                            for (UserClient client :
                                    list) {
                                System.out.println(client);
                                if (!client.getStatus().equals("E")) {
                                    disableduser.add(client);
                                }
                            }
                        }
                        adapter = new AllClientAdapter(disableduser,activity);
                        recyclerView.setAdapter(adapter);
                        break;

                    case 401 :
                        Utils.Message(response.message(),activity);
                        SessionManagement.getSessionManagement(activity).logoutUser(activity);
                        break;
                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(DisabledClients.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );

                }
            }

            @Override
            public void onFailure(Call<List<UserClient>> call, Throwable t) {
                Utils.Message("512" + t.getMessage(),activity);
            }
        });
    }
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }
}