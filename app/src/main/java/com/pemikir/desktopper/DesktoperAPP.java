package com.pemikir.desktopper;

import android.app.Application;
import android.content.Intent;

import com.pemikir.desktopper.Model.Constant;
import com.pemikir.desktopper.Rectrofit.iDesktoper;
import com.startapp.android.publish.StartAppSDK;

import retrofit.RestAdapter;

/**
 * Created by iconflux-android on 10/31/2015.
 */
public class DesktoperAPP extends Application {

    iDesktoper git;

    @Override
    public void onCreate() {
        super.onCreate();
        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(Constant.DesktopperURL).build();
        git = restAdapter.create(iDesktoper.class);
        StartAppSDK.init(this, "111685543", "211760560", true);
    }

    public iDesktoper getGit() {
        return git;
    }

}
