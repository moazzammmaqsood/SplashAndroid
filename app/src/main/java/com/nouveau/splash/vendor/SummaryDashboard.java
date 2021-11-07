package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.vendor.SummaryMonthly;
import com.nouveau.splash.R;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummaryDashboard extends AppCompatActivity {

    private static final String TAG = "VENDORDASHBOARD";
    TextView vendorname ;
    TextView date;
    VendorApi vendorApi;
    TextView totalpay,totbottles,activeclients, selectdate,fetch,monthdetail;
    Activity activity;
    ProgressBar progressbar;
    ImageView back;
    DatePickerDialog.OnDateSetListener datePicker;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_dashboard);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        vendorname =  findViewById(R.id.vendorname);
        date= findViewById(R.id.date);
        progressbar=findViewById(R.id.progressbar);
        totalpay=findViewById(R.id.totalpay);
        totbottles=findViewById(R.id.totbottles);
        activeclients=findViewById(R.id.activeclients);
        selectdate =findViewById(R.id.month);
        fetch=findViewById(R.id.fetch);
        monthdetail=findViewById(R.id.monthdetail);
        activity=this;
        vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);

        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();


        datePicker= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                if (selectdate != null) {
                    selectdate.setText(Utils.getDatetoString(myCalendar.getTime()));
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

    private void fetchSummary(String token , String date) {
        Call<SummaryMonthly> vendor = vendorApi.v1FetchMonthlySummary(token, date);
        showProgress();
        vendor.enqueue(new Callback<SummaryMonthly>() {
            @Override
            public void onResponse(Call<SummaryMonthly> call, Response<SummaryMonthly> response) {

                switch(response.code())  {
                    case 200:
                        Log.e(TAG, "onResponse: "+response.message() );
                        totalpay.setText(String.valueOf(response.body().getTotalpayments()));
                        totbottles.setText(String.valueOf(response.body().getTotalbottles()));
                        activeclients.setText(String.valueOf(response.body().getTotalactiveclients()));
                        monthdetail.setText("Summary for  the month of : "+new SimpleDateFormat("MMM").format(myCalendar.getTime()));
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