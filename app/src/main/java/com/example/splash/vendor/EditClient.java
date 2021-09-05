package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.splash.Api.VendorImpl;
import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.Api.modal.vendor.ClientRequest;
import com.example.splash.Api.modal.vendor.EditClientRequest;
import com.example.splash.R;
import com.example.splash.callbacks.ClientCallback;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditClient extends AppCompatActivity implements ClientCallback {
    private static final String TAG = "AddClient";
    final Calendar myCalendar = Calendar.getInstance();
    EditText fullname,contactno,address,daysdel,rate,deposit,bottles;
    RadioButton radioyes,radiono;
    LinearLayout rcvpaymentbtn;
    ProgressBar progressbar;
    LinearLayout bottlelastdelll,bottledelll,bottlerecll,paymentll;
    VendorApi vendorApi;
    SessionManagement sessionManagement;
    RadioGroup radioGroup;
    String oncall="Y";
    DatePickerDialog.OnDateSetListener datePicker;
    boolean old;
    ClientCallback clientCallback;
    VendorImpl vendorImpl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        setUi();
        clientCallback=this;
        Intent intent=getIntent();
        final int userid = intent.getIntExtra("userid",0);
        final int clientid = intent.getIntExtra("clientid",0);

        if(userid==0|| clientid==0){
            finish();
        }

        SplashUser user =SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        final String token = Utils.getToken(user.getToken());
        vendorImpl= new VendorImpl();
        showProgress();
        vendorImpl.FetchEditClient(clientCallback, Utils.getToken(user.getToken()),clientid,userid);



        rcvpaymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: clicked" );
                String Fnametext= fullname.getText().toString();
                String Cnotext= contactno.getText().toString();
                String addresstext=address.getText().toString();
                String daysdeltext=daysdel.getText().toString();
                String ratetext=rate.getText().toString();
                String deposittext=deposit.getText().toString();
                String bottlestext=bottles.getText().toString();

                boolean verifydata=true;
                if(Fnametext==null){
                    verifydata=false;
                    Log.e(TAG, "onClick: Fname is null" );
                }
                if(daysdeltext==null){
                    verifydata=false;
                    Log.e(TAG, "onClick: daysdeltext is null" );
                }
                if(ratetext==null){
                    verifydata=false;
                    Log.e(TAG, "onClick: ratetext is null" );
                }
                if(deposittext==null){
                    verifydata=false;
                    Log.e(TAG, "onClick: deposittext is null" );
                } if(bottlestext==null){
                    verifydata=false;
                    Log.e(TAG, "onClick: bottlestext is null" );
                }
                EditClientRequest clientRequest=null;


                if(verifydata){

                    showProgress();
                    clientRequest=new EditClientRequest(userid,clientid,Fnametext,Cnotext,addresstext,null,Integer.parseInt(daysdeltext),Integer.parseInt(ratetext),Integer.parseInt(deposittext),Integer.parseInt(bottlestext),oncall);
                    Call<SuccessResponse> addclient=vendorApi.v1editclient(token,clientRequest);
                    addclient.enqueue(new Callback<SuccessResponse>() {
                        @Override
                        public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                            switch (response.code()) {
                                case 200 :
                                    hideProgress();
                                    Toast.makeText(getApplicationContext(),response.body().getSuccessMessage(),Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    hideProgress();
                                    Toast.makeText(getApplicationContext(),"default "+response.code(),Toast.LENGTH_LONG).show();
                                    break;

                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: "+t.getMessage());
                            hideProgress();
                        }
                    });
                }



            }
        });



    }



    private void updateUi(ClientDetails clientDetails){
        fullname.setText(clientDetails.getName());
        contactno.setText(clientDetails.getContact());
        address.setText(clientDetails.getAddress());
        daysdel.setText(String.valueOf(clientDetails.getDaysperdelivery()));
        rate.setText(String.valueOf(clientDetails.getRate()));
        deposit.setText(String.valueOf(clientDetails.getDeposit()));
        bottles.setText(String.valueOf(clientDetails.getBottles()));
        if (clientDetails.getOncall().equals("Y")){
            radioyes.setChecked(true);
            radiono.setChecked(false);
        }else {
            radioyes.setChecked(false);
            radiono.setChecked(true);
        }

    }
    private void setUi() {
        fullname= findViewById(R.id.fullname);
        contactno= findViewById(R.id.contactno);
        address= findViewById(R.id.address);
        daysdel= findViewById(R.id.daysdel);
        rate= findViewById(R.id.rate);
        deposit= findViewById(R.id.deposit);
        bottles= findViewById(R.id.bottles);
        radioyes=findViewById(R.id.radioyes);
        radiono=findViewById(R.id.radiono);
        rcvpaymentbtn=findViewById(R.id.rcvpaymentbtn);
        paymentll=findViewById(R.id.paymentll);
        bottlelastdelll=findViewById(R.id.bottlelastdelll);
        bottledelll=findViewById(R.id.bottledelll);
        bottlerecll=findViewById(R.id.bottlerecll);
        radioGroup=findViewById(R.id.radiogrp);
        progressbar=findViewById(R.id.progressbar);
    }
    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void ClientFetched(ClientDetails clientDetails) {

        updateUi(clientDetails);
        hideProgress();
    }

    @Override
    public void FailedClientFetched(int Code, String message) {
        Utils.Message(message,this);
        hideProgress();
    }
}