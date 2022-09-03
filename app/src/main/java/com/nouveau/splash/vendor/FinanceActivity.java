package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.Api.modal.SuccessResponse;
import com.nouveau.splash.Api.modal.vendor.ClientDetails;
import com.nouveau.splash.Api.modal.vendor.FinanceEntity;
import com.nouveau.splash.Api.modal.vendor.FinanceRequest;
import com.nouveau.splash.Api.modal.vendor.Orders;
import com.nouveau.splash.R;
import com.nouveau.splash.adapters.FinanceAdapter;
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

public class FinanceActivity extends AppCompatActivity implements OnItemClick {
    private static final String TAG = "FinanceActivity";
    final VendorApi vendorApi= ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
    String token;
    int userid;
    int clientid;

    Activity activity;
    RecyclerView recyclerView;
    OnItemClick onItemClick;
    TextView incomeView,expenseView,totalView , dateView , fetchbtn;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener datePicker;
    Date selectedDate ;
    ProgressBar progressbar;
    private static FinanceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        SplashUser user = SessionManagement.getSessionManagement(this).GetUser();
        token= Utils.getToken(user.getToken());
        setUi();
        activity=this;
        onItemClick=this;

        progressbar =findViewById(R.id.progressbar);
        fetchFinance();

        datePicker= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                if (dateView != null) {
                    dateView.setText(Utils.getDatetoString(myCalendar.getTime()));
                    selectedDate=myCalendar.getTime();
                }

            }
        };

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FinanceActivity.this,datePicker,myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        fetchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchFinance();
            }
        });
    }

    public void setData(int income ,int expense){
        int total=income-expense;
        incomeView.setText(String.valueOf(income));
        expenseView.setText(String.valueOf(expense));
        totalView.setText(String.valueOf(total));


    }
    public void setUi(){
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        incomeView=findViewById(R.id.income);
        expenseView=findViewById(R.id.expense);
        totalView=findViewById(R.id.total);
        dateView=findViewById(R.id.month);
        fetchbtn=findViewById(R.id.fetch);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }
    public void fetchFinance(){
        showProgress();
        if(selectedDate==null) selectedDate = Calendar.getInstance().getTime();
        Call<List<FinanceEntity>> financeCall= vendorApi.v1GetFinance(token,Utils.getDatetoString(selectedDate));

        financeCall.enqueue(new Callback<List<FinanceEntity>>() {
            @Override
            public void onResponse(Call<List<FinanceEntity>> call, Response<List<FinanceEntity>> response) {
                switch (response.code()){
                    case 200:
                        List<FinanceEntity> financeList = response.body();
                        List<FinanceEntity> finalList= new ArrayList<>();

                        int income=0;
                        int expense=0;
                        for (FinanceEntity finance :
                                financeList ) {


                                income=income+finance.getIncome();
                                expense=expense+finance.getExpense();



                        }

//                        if(adapter==null) {
                            adapter = new FinanceAdapter(financeList, activity, onItemClick);
                            recyclerView.setAdapter(adapter);
                            hideProgress();
//                        }else {
//                            adapter.updateData(financeList);
//                            adapter.notifyDataSetChanged();
//                        }

                        setData(income,expense);
//                        bottles=0;
//                        payment=0;
//                        for (Orders finalorder:
//                                finalList) {
//                            bottles=bottles+finalorder.getBottlesdelivered();
//                            payment=payment+finalorder.getPayment();
//                        }
//                        setData();
                        break;
                    default:
                        hideProgress();
                        Log.e(TAG, "onResponse: "+response.code() + response.message() );
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String message= jObjError.getString("message");
                            if(message.equals("No Data Found")){
                                adapter.updateData(new ArrayList<>());
                                setData(0,0);
                            }
                            Toast.makeText(FinanceActivity.this, message, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }

                }
            }

            @Override
            public void onFailure(Call<List<FinanceEntity>> call, Throwable t) {

            }
        });
    }

    @Override
    public void ClientFetched(ClientDetails clientDetails) {

    }

    @Override
    public void FailedClientFetched(int Code, String message) {

    }

    @Override
    public void DeleteOrder(int orderid) {
        Call<SuccessResponse> orderscall=vendorApi.v1DeleteFinance(token,orderid);
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