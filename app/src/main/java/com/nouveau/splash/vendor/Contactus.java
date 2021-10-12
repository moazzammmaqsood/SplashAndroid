package com.nouveau.splash.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nouveau.splash.Api.interfaces.LoginApi;
import com.nouveau.splash.R;
import com.nouveau.splash.utils.ApplicationInstance;
import com.nouveau.splash.utils.Utils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Contactus extends AppCompatActivity {

    private static final String TAG = "CONTACTUS";
    Activity activity;
    ProgressBar progressbar;
    TextView termsandcondition,text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        termsandcondition=findViewById(R.id.termsandcondition);
        text=findViewById(R.id.text);
        activity=this;
        final LoginApi loginApi= ApplicationInstance.getInstance().getRetrofitInstance().create(LoginApi.class);
        progressbar=findViewById(R.id.progressbar);
        Call<String> login= loginApi.contactus();
        showProgress();
        login.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                hideProgress();

                switch (response.code()) {
                    case 200:

                            String message=response.body();
                            if(message.contains("$$")){
                                Log.d(TAG, "onResponse: pp");
                                message=message.replaceAll("\\$\\$","\n");
                            }
                            text.setText(message);

                        break;
                    default:
                        System.out.println(response.body());
                        try {
                            Log.e(TAG,"chrck");
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(Contactus.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq " + e.getMessage());
                        }

                }
            }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    hideProgress();
                    t.printStackTrace();
                    Utils.Message(t.getLocalizedMessage(),Contactus.this);
                }
            });


                termsandcondition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://splash-0.flycricket.io/privacy.html?fbclid=IwAR2cNOxMvzNbbmzhOI220dX5wJz-JDQQyYxnTAOrrzZTjbhV2gR5OdNSVu0";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        // Note the Chooser below. If no applications match,
                        // Android displays a system message.So here there is no need for try-catch.
                        startActivity(Intent.createChooser(intent, "Browse with"));
                    }
                });

            }

            public void showProgress() {
                progressbar.setVisibility(View.VISIBLE);

            }

            public void hideProgress() {
                progressbar.setVisibility(View.GONE);
            }

        }