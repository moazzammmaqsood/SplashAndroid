package com.example.splash.Api.interfaces;

import com.example.splash.Api.modal.LoginRequest;
import com.example.splash.Api.modal.LoginResponse;
import com.example.splash.Api.modal.SuccessResponse;
import com.example.splash.Api.modal.vendor.BottleDelivered;
import com.example.splash.Api.modal.vendor.ClientDelivery;
import com.example.splash.Api.modal.vendor.ClientDetails;
import com.example.splash.Api.modal.vendor.ClientRequest;
import com.example.splash.Api.modal.vendor.ClientUpdateRequest;
import com.example.splash.Api.modal.vendor.EditClientRequest;
import com.example.splash.Api.modal.vendor.Orders;
import com.example.splash.Api.modal.vendor.SummaryDaily;
import com.example.splash.Api.modal.vendor.SummaryDelivery;
import com.example.splash.Api.modal.vendor.UserClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VendorApi {

    @Headers("Content-Type:application/json")
    @POST("/api/v1/private/vendor/add_client")
    Call<SuccessResponse> v1addclient(@Header ("Authorization")String token, @Body ClientRequest request);

    @Headers("Content-Type:application/json")
    @GET("/api/v1/private/vendor/getclients")
    Call<List<UserClient>> v1getclients(@Header ("Authorization")String token);


    @Headers("Content-Type:application/json")
    @POST("/api/v1/private/vendor/update_client")
    Call<SuccessResponse> v1updateclient(@Header ("Authorization")String token,@Body ClientUpdateRequest client);

    @Headers("Content-Type:application/json")
    @POST("/api/v1/private/vendor/deliver")
    Call<SuccessResponse> v1deliverclient(@Header ("Authorization")String token,@Body BottleDelivered delivery);


    @Headers("Content-Type:application/json")
    @GET( "/api/v1/private/vendor/getdelivery"    )
    Call<List<ClientDelivery>> v1getdeliveries(@Header("Authorization")String token);


    @Headers("Content-Type:application/json")
    @GET( "/api/v1/private/vendor/getclient/{clientid}/{userid}"     )
    Call<ClientDetails> v1getclientbyid(@Header ("Authorization")String token,@Path("clientid") int clientid, @Path("userid") int userid ) ;


    @Headers("Content-Type:application/json")
    @GET( "/api/v1/private/vendor/get_client_orders/{clientid}" )
    Call<List<Orders>> v1getClientOrders(@Header ("Authorization")String token,@Path("clientid") int clientid);

    @Headers("Content-Type:application/json")
    @GET( "/api/v1/private/vendor/delete_get_client_order/{orderid}" )
    Call<SuccessResponse> v1deleteClientOrder(@Header ("Authorization")String token,@Path("orderid") int orderid);

    @Headers("Content-Type:application/json")
    @POST( "/api/v1/private/vendor/edit_client")
    Call<SuccessResponse> v1editclient(@Header ("Authorization")String token,@Body EditClientRequest client) ;

    @Headers("Content-Type:application/json")
    @GET("/api/v1/private/vendor/get_vendor_summary_deliveries/{date}")
    Call<List<SummaryDelivery>> v1getvendorsummary_deliveries(@Header ("Authorization")String token,@Path("date") String date);

    @Headers("Content-Type:application/json")
    @GET("/api/v1/private/vendor/get_vendor_summary/{date}")
    Call<SummaryDaily> v1getvendordatewissummary(@Header ("Authorization")String token, @Path("date") String date);

    @Headers("Content-Type:application/json")
    @GET("/api/v1/private/vendor/disable_client/{clientid}")
    Call<SuccessResponse> v1disableUser(@Header ("Authorization")String token, @Path("clientid") int clientid);

    @Headers("Content-Type:application/json")
    @GET("/api/v1/private/vendor/enable_client/{clientid}")
    Call<SuccessResponse> v1enableUser(@Header ("Authorization")String token, @Path("clientid") int clientid);

}