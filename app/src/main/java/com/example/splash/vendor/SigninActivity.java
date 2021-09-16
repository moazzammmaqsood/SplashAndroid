package com.example.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splash.Api.interfaces.LoginApi;
import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.LoginRequest;
import com.example.splash.Api.modal.LoginResponse;
import com.example.splash.Api.modal.SplashUser;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.R;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;
import com.example.splash.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

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
                                    Intent i=new Intent(activity,VenderDashboard.class);
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