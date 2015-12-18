package com.pemikir.desktopper.Activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;
import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Utility.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.startapp.android.publish.Ad;
import com.startapp.android.publish.AdEventListener;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.nativead.StartAppNativeAd;

public class FullscreenWallpaper extends AppCompatActivity {

    String getURL, wallpaperTITLE, toolbarTITLE;
    ImageView fullImage;
    Bitmap bitmap;
    FloatingActionButton setdesktop, savedesktop, share;

    TextView wallpapertitle;
    FloatingActionMenu menu;

    GoogleProgressBar googleprogressbar;
    Toolbar toolbar;
    private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
    private StartAppAd startAppAd = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_wallpaper);

        startAppAd.loadAd(new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                System.out.println("Ad received");
//                Toast.makeText(getApplicationContext(), "received", Toast.LENGTH_SHORT).show();
                startAppAd.showAd();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
//                Toast.makeText(MainDrawerActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menu = (FloatingActionMenu) findViewById(R.id.menu);

        setdesktop = (FloatingActionButton) findViewById(R.id.setdesktop);
        savedesktop = (FloatingActionButton) findViewById(R.id.savedesktop);
        share = (FloatingActionButton) findViewById(R.id.share);

        googleprogressbar = (GoogleProgressBar) findViewById(R.id.google_progress);
        googleprogressbar.bringToFront();

        getURL = getIntent().getStringExtra("url");
        wallpaperTITLE = getIntent().getStringExtra("wallpapertitle");


        fullImage = (ImageView) findViewById(R.id.fullimage);
        wallpapertitle = (TextView) findViewById(R.id.wallpapertitle);

        Log.i(FullscreenWallpaper.class.getSimpleName(), getURL);
//        fullImage.bringToFront();

        Picasso.with(this).load(getURL).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.image).into(fullImage, new Callback() {
            @Override
            public void onSuccess() {
                bitmap = ((BitmapDrawable) fullImage.getDrawable()).getBitmap();

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        setMenuButton(palette);
                    }
                });
                if (!TextUtils.isEmpty(wallpaperTITLE)) {
                    setWallpaperTitle(wallpaperTITLE);

                } else {
                    wallpapertitle.setVisibility(View.GONE);
                }
                toolbar.bringToFront();
                googleprogressbar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(), "NetworkError..", Toast.LENGTH_SHORT).show();
            }
        });

//        Picasso.with(this).load(getURL).into(fullImage);
    }

    public void setMenuButton(Palette palette) {
        menu.setVisibility(View.VISIBLE);
        menu.setIconAnimated(true);
        menu.setMenuButtonColorNormal(palette.getMutedColor(getResources().getColor(R.color.colorAccent)));
        menu.setMenuButtonColorRipple(palette.getDarkMutedColor(getResources().getColor(R.color.colorAccent)));

        setdesktop.setColorNormal(palette.getDarkMutedColor(getResources().getColor(R.color.black_40)));
        savedesktop.setColorNormal(palette.getDarkMutedColor(getResources().getColor(R.color.black_50)));
        share.setColorNormal(palette.getDarkMutedColor(getResources().getColor(R.color.black_70)));


    }

    public void setDesktop(View view) {
        try {
            new Utils(FullscreenWallpaper.this).setAsWallpaper(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveDesktop(View view) {
        try {
            new Utils(FullscreenWallpaper.this).saveImageToSDCard(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWallpaperTitle(String title) {
        wallpapertitle.setVisibility(View.VISIBLE);
        wallpapertitle.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ShareWallpaper(View view) {
        try {
            new Utils(FullscreenWallpaper.this).shareBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
