package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView bannerImage;
    String bannerImageLink;
    DigitalServer digitalServer;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    TextView loadingText;
    String uuid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        digitalServer = DigitalServer.retrofit.create(DigitalServer.class);
        bannerImage = findViewById(R.id.bannerImage);
        relativeLayout = findViewById(R.id.relativeLayout);
        progressBar = findViewById(R.id.progressBar);
        loadingText = findViewById(R.id.loadinText);

        progressBar.setVisibility(View.VISIBLE);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!uuid.equals(""))
                {
                    updateClick(uuid);
                }
            }
        });


        Call<BannerModel> call = digitalServer.getBanner();
        call.enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {

                if (response.isSuccessful()) {
                    BannerModel bannerModel = response.body();
                    bannerImageLink = bannerModel.getBanner_url();
                    Log.d("BannerId",bannerModel.getUuid());
                    Log.d("BannerId",bannerModel.getBanner_url());
                    if (bannerModel != null) {

                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.GONE);
                        Glide
                                .with(MainActivity.this)
                                .load(bannerImageLink)
                                .into(bannerImage);
                        updateImpression(bannerModel.getUuid());
                        uuid = bannerModel.getUuid();
                       // updateClick(bannerModel.getBanner_url());

                    }

                }
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loadingText.setText(t.getMessage());
              //  Toast.makeText(MainActivity.this, "banner found failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateImpression(String uuid)
    {
        Call<ResponseBody> call = digitalServer.updateImpression(uuid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

              //  Log.d("BannerId","up"+response.code());
                if(response.isSuccessful())
                {
                    ResponseBody responseBody = response.body();
                    Log.d("BannerId",responseBody.toString());
                  //  Toast.makeText(MainActivity.this, "impression added", Toast.LENGTH_SHORT).show();
                }else
                {
                    Log.d("BannerId","inside"+response.message().toString()+"Code"+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("BannerId","failure"+t.getMessage().toString());
              //  Toast.makeText(MainActivity.this, "Failed impression add", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateClick(String uuid)
    {
        Call<ResponseBody> call = digitalServer.updateClick(uuid);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

               // Log.d("BannerId","up"+response.code());
                if(response.isSuccessful())
                {
                    ResponseBody responseBody = response.body();
                    Log.d("BannerId",responseBody.toString());
                    Intent defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER);
                    Uri uri = Uri.parse(bannerImageLink);
                    defaultBrowser.setData(uri);
                    startActivity(defaultBrowser);
                 //   Toast.makeText(MainActivity.this, "Click added", Toast.LENGTH_SHORT).show();
                }else
                {
                    Log.d("BannerId","inside"+response.message().toString()+"Code"+response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("BannerId","failure"+t.getMessage().toString());
              //  Toast.makeText(MainActivity.this, "Failed Click add", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}