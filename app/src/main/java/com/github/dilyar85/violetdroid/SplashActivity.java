package com.github.dilyar85.violetdroid;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.avos.avoscloud.AVUser;

/**
 * Splash activity, for initializing data and preparing to open the app
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int SPLASH_TIME_OUT = 1500;

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent;
                //Check if user logged in before, if so starts MainActivity, otherwise starts LoginActivity
                if (AVUser.getCurrentUser() == null)
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                else
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}





