package com.jisa.stepintwoit.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.jisa.stepintwoit.R;
import com.jisa.stepintwoit.utils.SharedpreferenceUtils;
import com.jisa.stepintwoit.utils.Utils;

public class SplashScreenActivity extends AppCompatActivity {

    private int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Stetho.initializeWithDefaults(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    //checking if the email id is registered or not
    private void checkUser() {
        SharedpreferenceUtils sharedpreferenceUtils = new SharedpreferenceUtils(this);
        String emailId = sharedpreferenceUtils.getValue(Utils.KEY_EMAILID);
        if (emailId == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }


}


