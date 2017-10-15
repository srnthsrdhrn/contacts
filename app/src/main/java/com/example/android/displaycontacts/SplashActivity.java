package com.example.android.displaycontacts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash_img screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                if (pref.getBoolean("first_time",true)){
                    editor.putBoolean("first_time",false);
                    editor.apply();
                    startActivity(new Intent(SplashActivity.this,FirstTimeActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }

                  }
        }, 3000);
    }
}
