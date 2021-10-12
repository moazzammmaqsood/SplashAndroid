package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nouveau.splash.Api.VendorImpl;
import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.SuccessResponse;
import com.nouveau.splash.Api.modal.vendor.BottleDelivered;
import com.nouveau.splash.Api.modal.vendor.ClientDetails;
import com.nouveau.splash.R;
import com.nouveau.splash.callbacks.ViewClientCallback;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewClient extends AppCompatActivity implements ViewClientCallback {
    DatePickerDialog.OnDateSetListener datePicker;
    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout deliverBtn,orderBtn,crossBtn,okBtn;
    TextView clientname,clientcontact,clientaddress,clientbottlesdel,clientbottles,clientbottlesrate,clientlastdel,clientdaysdel,clientpaid,clientbalance;
    Context context;
    TextView date,nametag;
    int bottledel,bottlerec,bottlepay;
    String bottledate;
    VendorImpl vendorImpl;
    String token;
    int clientid,userid;
    ProgressBar progressbar;
    ViewClientCallback callback;
    ImageView editclient,back;
     VendorApi vendorApi;
     String clientnameDialog;
    Call<ClientDetails> call;
    private String TAG="ViewClient";
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);
        context=this;
        callback=this;
        Intent intent=getIntent();
         clientid=  intent.getIntExtra("clientid",-1);
         userid= intent.getIntExtra("userid",-1);
       vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        progressbar=findViewById(R.id.progressbar);
        if(clientid==-1 && userid==-1){
            Utils.Message("Failed to load user",this);
        }

        vendorImpl=new VendorImpl();
        initUi();




        final SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            finish();
        }

        if(user.getStatus().equals("E")){
            showDisable();
        }else{
            showEnable();
        }
        token=Utils.getToken(user.getToken());




        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                vendorImpl.EnableUser(callback,token,clientid);
            }
        });

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                vendorImpl.DisableUser(callback,token,clientid);
            }
        });

      //        Call<SuccessResponse> v1deliverclient= vendorApi.v1deliverclient()


        datePicker= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if(date!=null) {
                    date.setText(Utils.getDatetoString(myCalendar.getTime()));
                }


            }

        };

        deliverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewClient.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.deliver_dailog, viewGroup, false);
                builder.setView(dialogView);
                final EditText deliverbottle,recievedbottle,payment;
                final TextView deliver,cancle;
                final ProgressBar progressbardialog= dialogView.findViewById(R.id.progressbar);
                deliverbottle=  dialogView.findViewById(R.id.deliverbottle);
                recievedbottle= dialogView.findViewById(R.id.recievebottle);
                nametag= dialogView.findViewById(R.id.nametag);
                if (clientnameDialog!=null){
                    nametag.setText(clientnameDialog);
                }
                payment=dialogView.findViewById(R.id.receivepayment);
                date= dialogView.findViewById(R.id.deliverydate);
                deliver= dialogView.findViewById(R.id.deliver);
                cancle=dialogView.findViewById(R.id.cancel_button);
                date.setText(Utils.getDatetoString(myCalendar.getTime()));
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ViewClient.this,datePicker,myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                deliver.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tempdel,temprec,temppay;
                        tempdel=deliverbottle.getText().toString();
                        temprec=recievedbottle.getText().toString();
                        temppay=payment.getText().toString();
                        boolean del=false,rec=false,pay=false;
                        if(tempdel!=null || tempdel.isEmpty()){
                            bottledel=Integer.parseInt(tempdel);
                            del=true;
                        }else {
                            Utils.Message("Bottles Deliver Cannot be empty",context);
                        }

                        if(temprec!=null || temprec.isEmpty()){
                            bottlerec=Integer.parseInt(temprec);
                            rec=true;
                        }else {
                            Utils.Message("Bottles Deliver Cannot be empty",context);
                        }

                        if(temppay!=null || temppay.isEmpty()){
                            bottlepay=Integer.parseInt(temppay);
                            pay=true;
                        }else {
                            Utils.Message("Bottles payment Cannot be empty please write 0 instead",context);
                        }

                        if(del && rec && pay){
                            BottleDelivered deliveredobj=new BottleDelivered(clientid,bottledel,bottlerec,bottlepay,date.getText().toString());
                            Call<SuccessResponse> call=vendorApi.v1deliverclient(token,deliveredobj);
                            progressbardialog.setVisibility(View.VISIBLE);
                            deliver.setClickable(false);
                            call.enqueue(new Callback<SuccessResponse>() {
                                @Override
                                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                    switch (response.code()) {
                                        case 200:
                                            Utils.Message(response.body().getSuccessMessage(),context);
                                            progressbardialog.setVisibility(View.GONE);
                                            deliver.setClickable(true);
                                            FetchClient();
                                            closeDialogbox();


                                            break;
                                        default:
                                            Utils.Message(response.code()+response.message(),context);
                                            progressbardialog.setVisibility(View.GONE);
                                            deliver.setClickable(true);
                                    }
                                }

                                @Override
                                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                    Utils.Message(t.getMessage(),context);
                                    progressbardialog.setVisibility(View.GONE);
                                    deliver.setClickable(true);

                                }
                            });
                        }
                    }
                });



                alertDialog = builder.create();
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });

                alertDialog.show();
            }
        });


        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),ClientDeliveries.class);
                intent.putExtra("clientid",clientid);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
    }
    private void closeDialogbox(){
        if (alertDialog!=null)
        alertDialog.dismiss();
    }

    private void  initUi(){
        clientname =  findViewById(R.id.clientname);
        clientcontact=  findViewById(R.id.clientcontact);
        clientaddress=  findViewById(R.id.clientaddress);
        clientbottlesdel=  findViewById(R.id.clientbottlesdel);
        clientbottles=  findViewById(R.id.clientbottles);
        clientbottlesrate=  findViewById(R.id.clientbottlesrate);
        clientlastdel=  findViewById(R.id.clientlastdel);
        clientdaysdel=  findViewById(R.id.clientdaysdel);
        clientpaid=  findViewById(R.id.clientpaid);
        clientbalance=  findViewById(R.id.clientbalance);
        editclient=findViewById(R.id.editclient);
        back=findViewById(R.id.back);
        deliverBtn= findViewById(R.id.deliverBtn);
        orderBtn=findViewById(R.id.orderBtn);
        crossBtn=findViewById(R.id.crossBtn);
        okBtn= findViewById(R.id.okBtn);



        editclient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,EditClient.class);
                intent.putExtra("clientid",clientid);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void FetchClient(){
        showProgress();
        call = vendorApi.v1getclientbyid(token,clientid,userid);
        call.enqueue(new Callback<ClientDetails>() {
            @Override
            public void onResponse(Call<ClientDetails> call, Response<ClientDetails> response) {

                switch (response.code()){

                    case 200:

                        updateUi(response.body());
                        hideProgress();
                        break;

                    default:
                        Utils.Message(response.code()+response.message(),context);
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Utils.Message( jObjError.getString("message"),ViewClient.this);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                        hideProgress();
                        break;

                }
            }

            @Override
            public void onFailure(Call<ClientDetails> call, Throwable t) {
                Utils.Message(t.getMessage(),context);
                hideProgress();
            }
        });

    }

    private void updateUi(ClientDetails body) {

        clientname.setText(body.getName());
        clientcontact.setText(body.getContact());
        clientaddress.setText(body.getAddress());
        clientbottlesdel.setText(String.valueOf(body.getTotalbottles()));
        clientbottles.setText(String.valueOf(body.getBottlesholding()));
        clientbottlesrate.setText(String.valueOf(body.getRate()));
        clientlastdel.setText(body.getLastdelivery());
        clientdaysdel.setText(String.valueOf(body.getDaysperdelivery()));
        clientpaid.setText(String.valueOf(body.getPaid()));
        clientbalance.setText(String.valueOf(body.getPaymentremaining()));
        if(nametag!=null){
            nametag.setText(body.getName());

        }
        clientnameDialog =body.getName();
    }
    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }


    private void showEnable(){
        okBtn.setVisibility(View.VISIBLE);
        crossBtn.setVisibility(View.GONE);
    }

    private void showDisable(){
        okBtn.setVisibility(View.GONE);
        crossBtn.setVisibility(View.VISIBLE);
    }
    @Override
    public void succesfullyDisabledUser() {
        hideProgress();
        Utils.Message("Succesfully Disabled User",this);
        showEnable();
    }

    @Override
    public void succesfullyEnabledUser() {
        hideProgress();
        Utils.Message("Succesfully Enabled User",this);
        showDisable();
    }

    @Override
    public void unsuccessful(int responsecode, String message) {
        hideProgress();
        Utils.Message(message+" "+responsecode,this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        FetchClient();
    }
}
