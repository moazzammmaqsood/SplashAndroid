package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.Api.modal.vendor.ClientRequest;
import com.example.splash.R;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddClient extends AppCompatActivity {

    private static final String TAG = "AddClient";
    final Calendar myCalendar = Calendar.getInstance();
    EditText fullname,contactno,address,daysdel,rate,deposit,bottles,lastdelivery,lastbottledel,lastbottlerec,lastpayment;
    Switch Switchbtn;
    RadioButton radioyes,radiono;
    Boolean switchState;
    LinearLayout rcvpaymentbtn;
    ProgressBar progressbar;
    LinearLayout bottlelastdelll,bottledelll,bottlerecll,paymentll;
     VendorApi vendorApi;
     SessionManagement sessionManagement;
     RadioGroup radioGroup;
     String oncall="Y";
     DatePickerDialog.OnDateSetListener datePicker;
     ImageView back;
     boolean old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        setUi();
        setNewUi();
        switchState  = Switchbtn.isChecked();
        sessionManagement=SessionManagement.getSessionManagement(this);


        SplashUser splashUser=sessionManagement.GetUser();
        if(splashUser==null){
            Log.e(TAG, "onCreate: splashuser null" );
        }
        final String token= Utils.getToken(splashUser.getToken());

        vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    setOldUi();

                }else {

                    setNewUi();
                }
                old =b;

            }
        });

        datePicker= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if(lastdelivery!=null) {
                    lastdelivery.setText(Utils.getDatetoString(myCalendar.getTime()));
                }


            }

        };
        lastdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddClient.this,datePicker,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
               String lastdeliverytext=lastdelivery.getText().toString();
               String lastbottledeltext= lastbottledel.getText().toString();
               String lastpaymenttext =lastpayment.getText().toString();
               String lastbottlerectext=lastbottlerec.getText().toString();

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
               ClientRequest clientRequest=null;
               if(!old){
                   if(verifydata){
                        clientRequest=new ClientRequest(Fnametext,Cnotext,addresstext,null,Integer.parseInt(daysdeltext),Integer.parseInt(ratetext),Integer.parseInt(deposittext),Integer.parseInt(bottlestext),oncall);
                   }
               }else{
                   if (lastdeliverytext==null || lastdeliverytext.isEmpty()){
                       verifydata=false;
                       Log.e(TAG, "onClick: lastdeliverytext is null" );
                   }
                   if (lastbottledeltext==null || lastbottledeltext.isEmpty()){
                       verifydata=false;
                       Log.e(TAG, "onClick: lastbottledeltext is null" );
                   } if(lastbottlerectext==null || lastbottlerectext.isEmpty()){
                       verifydata=false;
                       Log.e(TAG, "onClick: lastbottlerectext is null" );

                   } if(lastpaymenttext==null || lastpaymenttext.isEmpty()){
                       verifydata=false;
                       Log.e(TAG, "onClick: lastpaymenttext is null" );
                   }


                   if(verifydata){
                       clientRequest=new ClientRequest(Fnametext,Cnotext,addresstext,null,Integer.parseInt(daysdeltext),Integer.parseInt(ratetext),Integer.parseInt(deposittext),lastdeliverytext,Integer.parseInt(lastbottledeltext),Integer.parseInt(lastbottlerectext),Integer.parseInt(lastpaymenttext),Integer.parseInt(bottlestext),oncall);
                   }else{

                   }

               }
               if(verifydata){
                   showProgress();
                   Call<SuccessResponse> addclient=vendorApi.v1addclient(token,clientRequest);
                   addclient.enqueue(new Callback<SuccessResponse>() {
                       @Override
                       public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                           switch (response.code()) {
                               case 200 :
                                   hideProgress();
                                   Toast.makeText(getApplicationContext(),response.body().getSuccessMessage(),Toast.LENGTH_LONG).show();
                                   Intent intent =new Intent(AddClient.this,ClientActivity.class);
                                   startActivity(intent);
                                   finish();
                                   break;
                               default:
                                   hideProgress();
                                   try {
                                       JSONObject jObjError = new JSONObject(response.errorBody().string());
                                       Toast.makeText(AddClient.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                   } catch (Exception e) {
                                       Log.e(TAG, "onResponse:qq "+e.getMessage());
                                   }

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

    private void setUi() {
        fullname= findViewById(R.id.fullname);
        contactno= findViewById(R.id.contactno);
        address= findViewById(R.id.address);
        daysdel= findViewById(R.id.daysdel);
        rate= findViewById(R.id.rate);
        deposit= findViewById(R.id.deposit);
        bottles= findViewById(R.id.bottles);
        lastdelivery= findViewById(R.id.lastdelivery);
        lastbottledel= findViewById(R.id.lastbottledel);
        lastbottlerec= findViewById(R.id.lastbottlerec);
        lastpayment= findViewById(R.id.lastpayment);
        Switchbtn=findViewById(R.id.Switchbtn);
        radioyes=findViewById(R.id.radioyes);
        radiono=findViewById(R.id.radiono);
        rcvpaymentbtn=findViewById(R.id.rcvpaymentbtn);
        paymentll=findViewById(R.id.paymentll);
        bottlelastdelll=findViewById(R.id.bottlelastdelll);
        bottledelll=findViewById(R.id.bottledelll);
        bottlerecll=findViewById(R.id.bottlerecll);
        radioGroup=findViewById(R.id.radiogrp);
        progressbar=findViewById(R.id.progressbar);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SetRadiobtn();
    }
    private void setNewUi(){
        paymentll.setVisibility(View.GONE);
        bottlelastdelll.setVisibility(View.GONE);
        bottledelll.setVisibility(View.GONE);
        bottlerecll.setVisibility(View.GONE);
    }
    private void setOldUi(){
        paymentll.setVisibility(View.VISIBLE);
        bottlelastdelll.setVisibility(View.VISIBLE);
        bottledelll.setVisibility(View.VISIBLE);
        bottlerecll.setVisibility(View.VISIBLE);
    }

    private void SetRadiobtn(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioyes:
                        oncall="Y";
                        break;
                    case R.id.radiono:
                        oncall="N";
                        break;
                }
            }
        });
    }
    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}

