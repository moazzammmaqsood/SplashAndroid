package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nouveau.splash.Api.interfaces.LoginApi;
import com.nouveau.splash.Api.modal.LoginRequest;
import com.nouveau.splash.Api.modal.LoginResponse;
import com.nouveau.splash.Api.modal.SplashUser;
import com.nouveau.splash.R;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.SessionManagement;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = "SIGNIN";
    EditText username;
    EditText password;
    Button button;
    Context context;
    Activity activity;
    TextView terms;
    ProgressBar progressbar;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        activity =this;
        final LoginApi loginApi= ApplicationInstance.getInstance().getRetrofitInstance().create(LoginApi.class);
        context=this;
        username= findViewById(R.id.username);
        password=findViewById(R.id.password);
        button =findViewById(R.id.login);
        progressbar=findViewById(R.id.progressbar);
        terms=findViewById(R.id.terms);

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,Contactus.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName= username.getText().toString();
                String passWord= password.getText().toString();

                if(userName==null || userName.isEmpty()){
                    Utils.Message("Please Enter Username",context);
                }else if(passWord==null || passWord.isEmpty()){
                    Utils.Message("Please Enter Username",context);
                }else{
                    showProgress();
                    Call<LoginResponse> login= loginApi.login(new LoginRequest(userName,passWord));
                    login.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            hideProgress();

                            switch (response.code()) {
                                case 200:
                                    SplashUser user = new SplashUser(response.body());
                                    SessionManagement.getSessionManagement(activity).SetUser(user);
                                    Intent i=new Intent(activity,SummaryDashboard.class);
                                    startActivity(i);
                                    finish();
                                    break;
                                default:
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(SigninActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Log.e(TAG, "onResponse:qq "+e.getMessage());
                                    }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            hideProgress();
                            t.printStackTrace();
                            Utils.Message(t.getLocalizedMessage(),context);
                        }
                    });
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