package com.example.android.displaycontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash_img screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                    startActivity(new Intent(FirstTimeActivity.this,MainActivity.class));
                    finish();
            }
        }, 3000);
    }
}
