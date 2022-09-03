package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nouveau.splash.Api.VendorImpl;
import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.SuccessResponse;
import com.nouveau.splash.Api.modal.vendor.ClientDetails;
import com.nouveau.splash.Api.modal.vendor.Orders;

import com.nouveau.splash.R;
import com.nouveau.splash.adapters.ClientDeliveriesAdapter;
import com.nouveau.splash.callbacks.OnItemClick;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
     ProgressBar progressbar;

     int userid , clientid;
     ImageView back,history;
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
        progressbar=findViewById(R.id.progressbar);
        history= findViewById(R.id.history);
        if(clientid==0 || userid==0){
            Log.e(TAG, "onCreate: clientid or userid is null");
            finish();
        }
        SplashUser user= SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            Log.e(TAG, "onCreate: user is null");
            finish();
        }
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        VendorImpl vendorImpl=new VendorImpl();
        token= Utils.getToken(user.getToken());

        vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        vendorImpl.FetchClient(this,token,clientid,userid);


        setUi();


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 =new Intent(ClientDeliveries.this,History.class);
                intent1.putExtra("clientid",clientid);
                intent1.putExtra("userid",userid);
                startActivity(intent1);
            }
        });

    }

    public void fetchOrders(){
        Call<List<Orders>> ordersCall= vendorApi.v1getClientOrders(token,clientid);

        ordersCall.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                switch (response.code()){
                    case 200:
                        List<Orders> orders = response.body();
                        List<Orders> finalList= new ArrayList<>();
                        Date date= Calendar.getInstance().getTime();
                        for (Orders order :
                                orders ) {
                            if(order.getDate().getMonth()==date.getMonth()){
                                finalList.add(order);
                            }
                        }
                       adapter = new ClientDeliveriesAdapter(finalList,activity,onItemClick);
                        recyclerView.setAdapter(adapter);
                        bottles=0;
                        payment=0;
                        for (Orders finalorder:
                                finalList) {
                            bottles=bottles+finalorder.getBottlesdelivered();
                            payment=payment+finalorder.getPayment();
                        }
                        setData();
                        break;
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ClientDeliveries.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }

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
                        List<Orders> orders = response.body();
                        List<Orders> finalList= new ArrayList<>();
                        Date date= Calendar.getInstance().getTime();
                        for (Orders order :
                                orders ) {
                            if(order.getDate().getMonth()==date.getMonth() && order.getDate().getYear()==date.getYear()){
                                finalList.add(order);
                            }
                        }

                        adapter = new ClientDeliveriesAdapter(response.body(),activity,onItemClick);
                        recyclerView.setAdapter(adapter);
                        bottles=0;
                        payment=0;
                        for (Orders finalorder:
                                finalList) {
                            bottles=bottles+finalorder.getBottlesdelivered();
                            payment=payment+finalorder.getPayment();
                        }
                        setData();
                        break;
                    default:
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(ClientDeliveries.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }

                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                Toast.makeText(ClientDeliveries.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}