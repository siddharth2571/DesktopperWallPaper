package com.pemikir.desktopper.Rectrofit;


import com.pemikir.desktopper.BingWallpaperModel.BingWallpaperModel;
import com.pemikir.desktopper.Model.DesktoperModelResponce;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by iconflux-android on 10/29/2015.
 */
public interface iDesktoper {


    @GET("/wallpapers")
    void getAllWallpaper(Callback<DesktoperModelResponce> callback);

    @GET("/wallpapers")
    void getMoreWallpaper(@Query("page") int pageno, Callback<DesktoperModelResponce> callback);

    @GET("/")
    void getWallpaperOfTheDay(Callback<BingWallpaperModel> callback);


   /* @GET("/csa")
    void getMatchList(Callback<Response> callback);*/

}
