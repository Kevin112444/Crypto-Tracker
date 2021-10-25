package com.example.finalversion;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TetherInfo extends AppCompatActivity {
    private TextView mPrice;
    private static HttpsURLConnection connection;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetherinfo);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Tether");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#72bed4"));
        actionBar.setBackgroundDrawable(colorDrawable);


        OkHttpClient client = new OkHttpClient();
        mPrice = findViewById(R.id.priceContent);



        String url = "https://api.coingecko.com/api/v3/simple/price?ids=tether&vs_currencies=usd";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if (response.isSuccessful()){

                    final String myResponse = response.body().string();
                    String price = null;
                    JSONObject jsonResponse = null;
                    try{
                        jsonResponse = new JSONObject(myResponse);
                        price = jsonResponse.getJSONObject("tether").getString("usd");
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                    String Prices = price;
                    TetherInfo.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPrice.setText("$"+Prices);

                        }
                    });
                }
            }

        });
    }
}
