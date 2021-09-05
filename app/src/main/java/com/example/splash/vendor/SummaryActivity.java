package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.splash.Api.VendorImpl;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.vendor.SummaryDaily;
import com.example.splash.Api.modal.vendor.SummaryDelivery;
import com.example.splash.R;
import com.example.splash.adapters.SummaryDeliveryAdapter;
import com.example.splash.adapters.VendorDashboardAdapter;
import com.example.splash.callbacks.SummaryDeliveryCallBack;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import java.util.Calendar;
import java.util.List;

import okhttp3.internal.Util;

public class SummaryActivity extends AppCompatActivity implements SummaryDeliveryCallBack {

    TextView totalhouse,revenue,paid,bottlesdel,bottlesrec,fetch,selectdate;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    VendorImpl vendorImpl;
    SummaryDeliveryCallBack callBack;
    DatePickerDialog.OnDateSetListener datePicker;
    private static RecyclerView.Adapter adapter;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        setUi();
        callBack=this;
        vendorImpl =new VendorImpl();

        SplashUser user= SessionManagement.getSessionManagement(this).GetUser();
        if(user==null){
            SessionManagement.getSessionManagement(this).logoutUser(this);
        }
        final String token = Utils.getToken(user.getToken());

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
                new DatePickerDialog(SummaryActivity.this,datePicker,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectdate.getText().equals(getResources().getString(R.string.selectdate))){
                    Utils.Message("Please select Date",getApplicationContext());
                }else {

                    vendorImpl.FetchDatewiesSummary(callBack, token,Utils.getDatetoString(myCalendar.getTime()));
                    vendorImpl.FetchDatewiseSummaryDelivery(callBack, token,Utils.getDatetoString(myCalendar.getTime()));

                }
            }
        });

    }

    private void setUi() {
        totalhouse=findViewById(R.id.totalhouse);
        revenue=findViewById(R.id.revenue);
        paid=findViewById(R.id.paid);
        bottlesdel=findViewById(R.id.bottlesdel);
        bottlesrec=findViewById(R.id.bottlesrec);
        selectdate=findViewById(R.id.date);
        fetch =findViewById(R.id.fetch);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public void SuccessfullyFetchedSummary(SummaryDaily summary) {
        totalhouse.setText(String.valueOf(summary.getHouses()));
        revenue.setText(String.valueOf(summary.getRevenue()));
        paid.setText(String.valueOf(summary.getPayment()));
        bottlesdel.setText(String.valueOf(summary.getBottlesdelivered()));
        bottlesrec.setText(String.valueOf(summary.getBottlesrecieved()));
    }

    @Override
    public void SuccessfullyFetchedSummaryDelivery(List<SummaryDelivery> summaryDelivery) {

        adapter = new SummaryDeliveryAdapter(summaryDelivery,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void Failed(int Code, String message) {

    }
}