package com.example.finalversion;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#72bed4"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        TextView mDetailTv = findViewById(R.id.textView);

        //get data from previous activity
        Intent intent = getIntent();
        String mActionBarTitle = intent.getStringExtra("actionBarTitle");
        String mContent = intent.getStringExtra("contentTv");

        //actionbar title
        actionBar.setTitle(mActionBarTitle);
        //set text in textview
        mDetailTv.setText(mContent);
    }
}