package com.example.splash.Api;

import android.app.Activity;

import com.example.splash.Api.interfaces.VendorApi;
import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.callbacks.ClientCallback;
import com.example.splash.callbacks.OnItemClick;
import com.example.splash.utils.ApplicationInstance;
import com.example.splash.utils.SessionManagement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public  class VendorImpl  {



   public void FetchClient(final OnItemClick clientCallback, String token, int clientid, int userid) {
       VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
       Call<ClientDetails> clientcall = vendorApi.v1getclientbyid(token, clientid, userid);
       clientcall.enqueue(new Callback<ClientDetails>() {
           @Override
           public void onResponse(Call<ClientDetails> call, Response<ClientDetails> response) {
               switch (response.code()) {
                   case 200:
                       clientCallback.ClientFetched(response.body());
                       break;

                   default:
                       clientCallback.FailedClientFetched(response.code(), response.errorBody().toString());
               }
           }

           @Override
           public void onFailure(Call<ClientDetails> call, Throwable t) {
               clientCallback.FailedClientFetched(512, t.getMessage());
           }

       });

    }

    public void FetchEditClient(final ClientCallback clientCallback, String token, int clientid, int userid) {
        VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<ClientDetails> clientcall = vendorApi.v1getclientbyid(token, clientid, userid);
        clientcall.enqueue(new Callback<ClientDetails>() {
            @Override
            public void onResponse(Call<ClientDetails> call, Response<ClientDetails> response) {
                switch (response.code()) {
                    case 200:
                        clientCallback.ClientFetched(response.body());
                        break;

                    default:
                        clientCallback.FailedClientFetched(response.code(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ClientDetails> call, Throwable t) {
                clientCallback.FailedClientFetched(512, t.getMessage());
            }

        });

    }


}
