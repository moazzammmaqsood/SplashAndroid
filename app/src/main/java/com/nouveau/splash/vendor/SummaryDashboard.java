package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.SuccessResponse;
import com.nouveau.splash.Api.modal.vendor.FinanceRequest;
import com.nouveau.splash.Api.modal.vendor.SummaryMonthly;
import com.nouveau.splash.R;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryDashboard extends AppCompatActivity {

    private static final String TAG = "VENDORDASHBOARD";
    TextView vendorname ;
    TextView date,findate;
    VendorApi vendorApi;
    TextView totalpay,totbottles,activeclients, selectdate,fetch,monthdetail,monthlyIncome,monthlyExpense,monthlyTotal;
    Activity activity;
    ProgressBar progressbar;
    ImageView back;
    String token;
    Context context;
    boolean type;
    AlertDialog alertDialog;
    DatePickerDialog.OnDateSetListener datePicker;
    FloatingActionButton addBtn;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar financeCalander = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        context= this;
        vendorname =  findViewById(R.id.vendorname);
        date= findViewById(R.id.date);
        progressbar=findViewById(R.id.progressbar);
        totalpay=findViewById(R.id.totalpay);
        totbottles=findViewById(R.id.totbottles);
        activeclients=findViewById(R.id.activeclients);
        selectdate =findViewById(R.id.month);
        fetch=findViewById(R.id.fetch);
        addBtn=findViewById(R.id.addBtn);
        monthdetail=findViewById(R.id.monthdetail);
        monthlyIncome=findViewById(R.id.monthlyIncome);
        monthlyExpense=findViewById(R.id.monthlyExpense);
        monthlyTotal=findViewById(R.id.monthlyTotal);
        activity=this;
        vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();

        token=Utils.getToken(user.getToken());
        datePicker= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                financeCalander.set(Calendar.YEAR,year);
                financeCalander.set(Calendar.MONTH,monthOfYear);
                financeCalander.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                if (selectdate != null) {
                    selectdate.setText(Utils.getDatetoString(myCalendar.getTime()));
                }
                if(findate!=null){
                    findate.setText(Utils.getDatetoString(financeCalander.getTime()));
                }


            }
        };

        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SummaryDashboard.this,datePicker,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SummaryDashboard.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                final View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.finance_dialog, viewGroup, false);
                builder.setView(dialogView);
                final EditText amount,remarks;
                final Switch switchbtn;
                final TextView save,cancle;
                final ProgressBar progressbardialog= dialogView.findViewById(R.id.progressbar);
                amount=  dialogView.findViewById(R.id.amount);
                remarks= dialogView.findViewById(R.id.remarks);
                findate= dialogView.findViewById(R.id.findate);
                switchbtn=dialogView.findViewById(R.id.switchbtn);
                save=dialogView.findViewById(R.id.save);
                cancle=dialogView.findViewById(R.id.cancel_button);
                findate.setText(Utils.getDatetoString(Calendar.getInstance().getTime()));
                findate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(SummaryDashboard.this,datePicker,financeCalander
                                .get(Calendar.YEAR), financeCalander.get(Calendar.MONTH),
                                financeCalander.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                switchbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        type=b;
                    }
                });


                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(alertDialog!=null){
                            alertDialog.dismiss();
                        }
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (amount.getText() == null) {
                            Utils.Message("Amount can not be null", context);
                        } else if (findate.getText() == null) {
                            Utils.Message("Date Cannot be null", context);
                        } else {

                            FinanceRequest financeRequest = new FinanceRequest();
                            financeRequest.setAmount(Integer.parseInt(amount.getText().toString()));
                            financeRequest.setDate(Utils.getDatetoString(financeCalander.getTime()));
                            financeRequest.setRemark(remarks.getText().toString());
                            if(type) {
                                financeRequest.setType("E");
                            }else
                            {
                                financeRequest.setType("I");
                            }
                            Call<SuccessResponse> call = vendorApi.v1AddFinance(token, financeRequest);
                            progressbardialog.setVisibility(View.VISIBLE);
                            save.setClickable(false);
                            call.enqueue(new Callback<SuccessResponse>() {
                                @Override
                                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                                    switch (response.code()) {
                                        case 200:
                                            Utils.Message(response.body().getSuccessMessage(), context);
                                            progressbardialog.setVisibility(View.GONE);
                                            save.setClickable(true);
                                            alertDialog.dismiss();
//                                        FetchClient();
//                                        closeDialogbox();

                                            break;
                                        default:
                                            Utils.Message(response.code() + response.message(), context);
                                            progressbardialog.setVisibility(View.GONE);
                                            save.setClickable(true);
                                    }
                                }

                                @Override
                                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                                    Utils.Message(t.getMessage(), context);
                                    progressbardialog.setVisibility(View.GONE);
//                                deliver.setClickable(true);

                                }
                            });
                        }
                    }
                });


               alertDialog= builder.show();

            }
        });


        if(user==null){
            Log.e(TAG, "onCreate: user null" );
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }

        vendorname.setText(user.getVendorname());
        date.setText(Utils.getDatetoStringformatted(new Date()));
        selectdate.setText(Utils.getDatetoString(new Date()));
        String token = Utils.getToken(user.getToken());

        fetchSummary(token,Utils.getDatetoString(new Date()));

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchSummary(token,Utils.getDatetoString(myCalendar.getTime()));
            }
        });

    }

    private void sendSms(){
        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        String token = Utils.getToken(user.getToken());
        Call<SuccessResponse> sms = vendorApi.v1sms(token);
        showProgress();
        sms.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                switch(response.code()) {
                    case 200:
                        hideProgress();
                        Utils.Message("Sms Sent",SummaryDashboard.this);
                        break;
                    case 401 :
                        Utils.Message(response.message(),activity);
                        SessionManagement.getSessionManagement(activity).logoutUser(activity);
                        break;
                    case 52:
                        Utils.Message(response.message(),activity);
                        break;
                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Utils.Message(jObjError.getString("message"),SummaryDashboard.this);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                        hideProgress();
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );


                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {

            }
        });
    }

    private void fetchSummary(String token , String date) {
        Call<SummaryMonthly> vendor = vendorApi.v1FetchMonthlySummary(token, date);
        showProgress();
        vendor.enqueue(new Callback<SummaryMonthly>() {
            @Override
            public void onResponse(Call<SummaryMonthly> call, Response<SummaryMonthly> response) {

                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        SummaryMonthly summary=response.body();
                        Long payments=response.body().getTotalpayments();

                        totalpay.setText(String.valueOf(payments));
                        totbottles.setText(String.valueOf(response.body().getTotalbottles()));
                        activeclients.setText(String.valueOf(response.body().getTotalactiveclients()));
                        monthdetail.setText("Summary for  the month of : "+new SimpleDateFormat("MMM").format(myCalendar.getTime()));
                        monthlyIncome.setText(String.valueOf(summary.getCountersale()));
                        monthlyExpense.setText(String.valueOf(summary.getExpense()));
                        Long total= summary.getCountersale()+payments-summary.getExpense();
                        monthlyTotal.setText(String.valueOf(total));
                        hideProgress();
                        break;
                    case 401 :
                        Utils.Message(response.message(),activity);
                        SessionManagement.getSessionManagement(activity).logoutUser(activity);
                        break;
                    case 52:
                        Utils.Message(response.message(),activity);
                        break;
                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Utils.Message(jObjError.getString("message"),SummaryDashboard.this);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                        hideProgress();
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );

                }
            }

            @Override
            public void onFailure(Call<SummaryMonthly> call, Throwable t) {
                Utils.Message(t.getMessage(),getApplicationContext());
            }

        });
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.dashboardmenu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (id == R.id.logout) {
                SessionManagement.getSessionManagement(activity).logoutUser(activity);

            } else if (id == R.id.allclients) {
                Intent i = new Intent(this, ClientActivity.class);
                startActivity(i);

            } else if (id == R.id.addclient) {
                Intent i = new Intent(this, AddClient.class);
                startActivity(i);
            } else if (id == R.id.summaary) {
                Intent i = new Intent(this, SummaryActivity.class);
                startActivity(i);
            } else if (id == R.id.disabledUser) {
                Intent i = new Intent(this, DisabledClients.class);
                startActivity(i);

            }else  if (id == R.id.sms){
                sendSms();
            }else if(id==R.id.finance){
                Intent i = new Intent(this, FinanceActivity.class);
                startActivity(i);

            }
            return super.onOptionsItemSelected(item);
        }



    public void showProgress(){
        progressbar.setVisibility(View.VISIBLE);

    }
    public void hideProgress(){
        progressbar.setVisibility(View.GONE);
    }
}