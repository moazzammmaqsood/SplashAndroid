package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.Api.modal.vendor.BottleDelivered;
import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.R;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewClient extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener datePicker;
    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout deliverBtn,orderBtn,crossBtn;
    TextView clientname,clientcontact,clientaddress,clientbottlesdel,clientbottles,clientbottlesrate,clientlastdel,clientdaysdel,clientpaid,clientbalance;
    Context context;
    EditText date;
    int bottledel,bottlerec,bottlepay;
    String bottledate;
    String token;
    int clientid,userid;
    ImageView editclient,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_client);
        context=this;
        Intent intent=getIntent();
         clientid=  intent.getIntExtra("clientid",-1);
         userid= intent.getIntExtra("userid",-1);
        final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        if(clientid==-1 && userid==-1){
            Utils.Message("Failed to load user",this);
        }

        initUi();




        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            finish();
        }

        token=Utils.getToken(user.getToken());


        Call<ClientDetails> call = vendorApi.v1getclientbyid(token,clientid,userid);
        call.enqueue(new Callback<ClientDetails>() {
            @Override
            public void onResponse(Call<ClientDetails> call, Response<ClientDetails> response) {

                switch (response.code()){

                    case 200:
                        updateUi(response.body());
                        break;

                    default:
                     Utils.Message(response.code()+response.message(),context);
                        break;

                }
            }

            @Override
            public void onFailure(Call<ClientDetails> call, Throwable t) {
                Utils.Message(t.getMessage(),context);
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
                TextView deliver,cancle;
                deliverbottle=  dialogView.findViewById(R.id.deliverbottle);
                recievedbottle= dialogView.findViewById(R.id.recievebottle);
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
                            call.enqueue(new Callback<SuccessResponse>() {
                                @Override
                                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                    switch (response.code()) {
                                        case 200:
                                            Utils.Message(response.body().getSuccessMessage(),context);
                                        default: Utils.Message(response.code()+response.message(),context);
                                    }
                                }

                                @Override
                                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                    Utils.Message(t.getMessage(),context);
                                }
                            });
                        }
                    }
                });



                final AlertDialog alertDialog = builder.create();
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
    private void updateUi(ClientDetails body) {

        clientname.setText(body.getName());
        clientcontact.setText(body.getContact());
        clientaddress.setText(body.getAddress());
        clientbottlesdel.setText(String.valueOf(body.getBottles()));
        clientbottles.setText(String.valueOf(body.getBottlesholding()));
        clientbottlesrate.setText(String.valueOf(body.getRate()));
        clientlastdel.setText(body.getLastdelivery());
        clientdaysdel.setText(String.valueOf(body.getDaysperdelivery()));
        clientpaid.setText(String.valueOf(body.getPaid()));
        clientbalance.setText(String.valueOf(body.getPaymentremaining()));

    }
}
