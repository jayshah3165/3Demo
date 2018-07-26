package com.wsusingretrofitdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.utils.Const;
import com.wsusingretrofitdemo.utils.Pref;
import com.wsusingretrofitdemo.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Utils.addActivities("SplashActivity", this);


      // Utils.GetFCMToken(this);


        new Thread() {
            public void run() {
                try {
                    sleep(SPLASH_TIME_OUT);
                    if (!Pref.getStringValue(SplashActivity.this, Const.PREF_USERID, "").equalsIgnoreCase(""))
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }

                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
