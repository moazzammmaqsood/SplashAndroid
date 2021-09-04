package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splash.Api.VendorImpl;
import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.Api.modal.vendor.Orders;
import com.example.splash.R;
import com.example.splash.adapters.ClientDeliveriesAdapter;
import com.example.splash.adapters.VendorDashboardAdapter;
import com.example.splash.callbacks.OnItemClick;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDeliveries extends AppCompatActivity implements OnItemClick {

    private static final String TAG = "ClientDeliveries";
    RecyclerView recyclerView;
    TextView clientname, clientbottle, clientpayment;
    int bottles,payment;
     VendorApi vendorApi;
     OnItemClick onItemClick;
     Activity activity;
     String token;
     int userid , clientid;
    private static RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_deliveries);
        onItemClick=this;
        activity=this;
        Intent intent =getIntent();
        userid=intent.getIntExtra("userid",0);
        clientid=intent.getIntExtra("clientid",0);
        if(clientid==0 || userid==0){
            Log.e(TAG, "onCreate: clientid or userid is null");
            finish();
        }
        SplashUser user= SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            Log.e(TAG, "onCreate: user is null");
            finish();
        }

        VendorImpl vendorImpl=new VendorImpl();
        token= Utils.getToken(user.getToken());

        vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        vendorImpl.FetchClient(this,token,clientid,userid);


        setUi();


    }

    public void fetchOrders(){
        Call<List<Orders>> ordersCall= vendorApi.v1getClientOrders(token,clientid);

        ordersCall.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                switch (response.code()){
                    case 200:
                       adapter = new ClientDeliveriesAdapter(response.body(),activity,onItemClick);
                        recyclerView.setAdapter(adapter);
                        bottles=0;
                        payment=0;
                        for (Orders orders:
                                response.body()) {
                            bottles=bottles+orders.getBottlesdelivered();
                            payment=payment+orders.getPayment();
                        }
                        setData();
                        break;
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );


                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
    }
    public void fetchClient(){
        Call<List<Orders>> ordersCall= vendorApi.v1getClientOrders(token,clientid);

        ordersCall.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                switch (response.code()){
                    case 200:
                        adapter = new ClientDeliveriesAdapter(response.body(),activity,onItemClick);
                        recyclerView.setAdapter(adapter);
                        bottles=0;
                        payment=0;
                        for (Orders orders:
                                response.body()) {
                            bottles=bottles+orders.getBottlesdelivered();
                            payment=payment+orders.getPayment();
                        }
                        setData();
                        break;
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );


                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {

            }
        });
    }

    private void setData() {
        clientbottle.setText(String.valueOf(bottles));
        clientpayment.setText(String.valueOf(payment));
    }

    public void setUi(){
        clientname=findViewById(R.id.clientname);
        clientbottle=findViewById(R.id.clientbottle);
        clientpayment=findViewById(R.id.clientpayment);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fetchOrders();
    }

    @Override
    public void ClientFetched(ClientDetails clientDetails) {
        clientname.setText(clientDetails.getName());

    }

    @Override
    public void FailedClientFetched(int Code, String message) {

    }

    @Override
    public void DeleteOrder(int orderid) {
        Call<SuccessResponse> orderscall=vendorApi.v1deleteClientOrder(token,orderid);
        ShowDialog(orderscall);
    }

    public void ShowDialog(final Call<SuccessResponse> orderscall){
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        orderscall.enqueue(new Callback<SuccessResponse>() {
                            @Override
                            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                               switch (response.code()) {
                                   case 200:
                                       Toast.makeText(getApplicationContext(),response.body().getSuccessMessage(),Toast.LENGTH_LONG).show();
                                       break;
                                   default:
                                       Log.e(TAG, "onResponse: "+response.code() );
                               }
                            }

                            @Override
                            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                Log.e(TAG, "onResponse: "+t.getMessage() );
                            }
                        });
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}