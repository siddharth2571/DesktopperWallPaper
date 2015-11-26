package com.pemikir.desktopper.Model;

import android.os.Environment;

import java.io.File;

/**
 * Created by iconflux-android on 10/31/2015.
 */
public class Constant {


    public static String DesktopperURL = "https://api.desktoppr.co/1/";
    public static String Christmasswallpaper = "https://api.desktoppr.co/1/users/christmaswallpaper";

//    ?safe_filter=all

    public static String BINGWallaper = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=en-US";

    public static String GalleryName = "";
    public static File myDir = new File(Environment.getExternalStorageDirectory(), GalleryName);

}
