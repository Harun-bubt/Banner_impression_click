package com.example.helloworld;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import kotlin.ParameterName;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface DigitalServer {

    //------------------ Base URL ------------------
    String BASE_URL = "http://bannerapi.digitalmgroup.com/";
    //----------------------------------------------

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    //For timeout calculation in calls
    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .callTimeout(6, TimeUnit.SECONDS)
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .writeTimeout(6, TimeUnit.SECONDS)
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();



    @GET("random-banner")
    Call<BannerModel> getBanner(

    );

  //  @Headers("Content-Type: text/html")
    @FormUrlEncoded
    @PATCH("update-impression")
    Call<ResponseBody> updateImpression(
            @Field("uuid") String uuid
    );


    @FormUrlEncoded
    @PATCH("update-click")
    Call<ResponseBody> updateClick(
            @Field("uuid") String uuid
    );



}
