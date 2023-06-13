package com.nouveau.splash.Api;

import android.util.Log;

import com.nouveau.splash.Api.interfaces.VendorApi;
import com.nouveau.splash.Api.modal.SuccessResponse;
import com.nouveau.splash.Api.modal.vendor.ClientDetails;
import com.nouveau.splash.Api.modal.vendor.PdfUrl;
import com.nouveau.splash.Api.modal.vendor.SummaryDaily;
import com.nouveau.splash.Api.modal.vendor.SummaryDelivery;
import com.nouveau.splash.callbacks.ClientCallback;
import com.nouveau.splash.callbacks.OnItemClick;
import com.nouveau.splash.callbacks.SummaryDeliveryCallBack;
import com.nouveau.splash.callbacks.ViewClientCallback;
import com.nouveau.splash.utils.ApplicationInstance;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public  class VendorImpl {


    private String TAG="VendorImpl";

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
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            clientCallback.FailedClientFetched(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }


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
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            clientCallback.FailedClientFetched(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                }
            }

            @Override
            public void onFailure(Call<ClientDetails> call, Throwable t) {
                clientCallback.FailedClientFetched(512, t.getMessage());
            }

        });

    }


    public void FetchDatewiesSummary(final SummaryDeliveryCallBack callBack, String token, String date) {
        VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<SummaryDaily> clientcall = vendorApi.v1getvendordatewissummary(token, date);
        clientcall.enqueue(new Callback<SummaryDaily>() {
            @Override
            public void onResponse(Call<SummaryDaily> call, Response<SummaryDaily> response) {
                switch (response.code()) {
                    case 200:
                        callBack.SuccessfullyFetchedSummary(response.body());
                        break;


                    default:   Log.e(TAG, "onResponse: "+response.code() + response.message() );
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            callBack.Failed(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }

                }
            }

            @Override
            public void onFailure(Call<SummaryDaily> call, Throwable t) {
                callBack.Failed(512, t.getMessage());
            }

        });
    }


    public void FetchDatewiseSummaryDelivery(final SummaryDeliveryCallBack callBack, String token, String date) {
        VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<List<SummaryDelivery>> clientcall = vendorApi.v1getvendorsummary_deliveries(token, date);
        clientcall.enqueue(new Callback<List<SummaryDelivery>>() {
            @Override
            public void onResponse(Call<List<SummaryDelivery>> call, Response<List<SummaryDelivery>> response) {
                switch (response.code()) {
                    case 200:
                        callBack.SuccessfullyFetchedSummaryDelivery(response.body());
                        break;

                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            callBack.Failed(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }          }
            }

            @Override
            public void onFailure(Call<List<SummaryDelivery>> call, Throwable t) {
                callBack.Failed(512, t.getMessage());
            }

        });

    }
        public void DisableUser(final ViewClientCallback callBack, String token, int clientid) {
            VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
            Call<SuccessResponse> clientcall = vendorApi.v1disableUser(token,clientid);
            clientcall.enqueue(new Callback<SuccessResponse>() {
                @Override
                public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                    switch (response.code()) {
                        case 200:
                            callBack.succesfullyDisabledUser();
                            break;

                        default:
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                callBack.unsuccessful(response.code(), jObjError.getString("message"));
                            } catch (Exception e) {
                                Log.e(TAG, "onResponse:qq "+e.getMessage());
                            }             }
                }

                @Override
                public void onFailure(Call<SuccessResponse> call, Throwable t) {
                    callBack.unsuccessful(512, t.getMessage());
                }

            });





    }
    public void EnableUser(final ViewClientCallback callBack, String token, int clientid) {
        VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<SuccessResponse> clientcall = vendorApi.v1enableUser(token, clientid);
        clientcall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response) {
                switch (response.code()) {
                    case 200:
                        callBack.succesfullyEnabledUser();
                        break;

                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            callBack.unsuccessful(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                      }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t) {
                callBack.unsuccessful(512, t.getMessage());
            }

        });
    }


    public void getMonthlyBill(final ViewClientCallback callback,String token,int clientId,int userId,String yearMonth){
        VendorApi vendorApi = ApplicationInstance.getInstance().getRetrofitInstance().create(VendorApi.class);
        Call<PdfUrl> clientcall = vendorApi.getMonthlyBill(token, clientId,userId,yearMonth);
        clientcall.enqueue(new Callback<PdfUrl>() {
            @Override
            public void onResponse(Call<PdfUrl> call, Response<PdfUrl> response) {
                switch (response.code()) {
                    case 200:
                        if(response.body()!=null)
                        callback.readyToViewPdf(response.body().getUrl());
                        else
                            callback.unsuccessful(response.code(), "Unable to find pdf url");
                        break;

                    default:
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            callback.unsuccessful(response.code(), jObjError.getString("message"));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse:qq "+e.getMessage());
                        }
                }
            }

            @Override
            public void onFailure(Call<PdfUrl> call, Throwable t) {
                callback.unsuccessful(512, t.getMessage());
            }

        });
    }
}
