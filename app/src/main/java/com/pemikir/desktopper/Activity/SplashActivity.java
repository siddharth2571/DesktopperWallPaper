package com.pemikir.desktopper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.pemikir.desktopper.DesktoperAPP;
import com.pemikir.desktopper.Model.DesktoperModelResponce;
import com.pemikir.desktopper.Pref.SessionManager;
import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Utility.Utils;

import retrofit.Callback;
import retrofit.RetrofitError;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    DesktoperAPP mApplication;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mApplication = (DesktoperAPP) getApplicationContext();

        // This method will be executed once the timer is over
        // Start your app main activity
        if (Utils.isConnectingToInternet(SplashActivity.this)) {

            mApplication.getGit().getAllWallpaper(new Callback<DesktoperModelResponce>() {
                @Override
                public void success(DesktoperModelResponce response1, retrofit.client.Response response) {

                    Log.i(MainActivity.class.getName(), Utils.convertResponseToString(response));
                    session = new SessionManager(SplashActivity.this);
                    session.setFirstjsonoffline(Utils.convertResponseToString(response));

                    Log.d("Sessionoffline", "=>" + session.getFirstjsonoffline());
                    DesktoperModelResponce desktopper = new Gson().fromJson(Utils.convertResponseToString(response), DesktoperModelResponce.class);
                    Log.d("Sessionoffline", "=>" + desktopper.getResponse().get(0).getImage().getUrl());
                    Intent i = new Intent(SplashActivity.this, MainDrawerActivity.class);
                    startActivity(i);

                    // close this activity
                    finish();
//                            Constant.responseListConstant = response1.getResponse();
                }

                @Override
                public void failure(RetrofitError error) {
//                Log.i(MainActivity.class.getName(), error.getResponse().toString());
//                Log.i(MainActivity.class.getName() + "Error", Utils.convertResponseToString(error.getResponse()));

                    Intent i = new Intent(SplashActivity.this, MainDrawerActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            });
        } else {

            Intent i = new Intent(SplashActivity.this, MainDrawerActivity.class);
            startActivity(i);
            // close this activity
            finish();
        }


    }


}
