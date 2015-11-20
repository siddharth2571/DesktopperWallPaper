package com.pemikir.desktopper.Activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pemikir.desktopper.BingWallpaperModel.BingWallpaperModel;
import com.pemikir.desktopper.DesktoperAPP;
import com.pemikir.desktopper.Model.Constant;
import com.pemikir.desktopper.Model.DesktoperModelResponce;
import com.pemikir.desktopper.Model.Response;
import com.pemikir.desktopper.Pref.SessionManager;
import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Rectrofit.iDesktoper;
import com.pemikir.desktopper.Utility.Utils;
import com.pemikir.desktopper.adapater.CardListAdapater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CardListAdapater.itemClickLister {

    Toolbar toolbar;
    FloatingActionButton fab;
    DesktoperAPP mApplication;
    RecyclerView rv_list_card;
    CardListAdapater adapter;
    int Counter = 0;
    ArrayList<Integer> selectedItemList = new ArrayList<>();
    List<Response> Responsemodel = Collections.emptyList();
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout wallpaperoftheaday;
    ImageView WallpaperOfTheDay_img;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maindrawer);

        session = new SessionManager(getApplicationContext());
        mApplication = (DesktoperAPP) getApplicationContext();
        init();
        setDrawerLayout();
        DesktoperModelResponce desktopper = new Gson().fromJson(session.getFirstjsonoffline(), DesktoperModelResponce.class);
        Log.d("Sessionoffline", "=>" + desktopper.getResponse().get(0).getImage().getUrl());
        Responsemodel = desktopper.getResponse();
        setAdapter();

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rv_list_card = (RecyclerView) findViewById(R.id.recycler_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                RefreshItem();
            }
        });


      /*  mApplication.getGit().getAllWallpaper(new Callback<DesktoperModelResponce>() {
            @Override
            public void success(DesktoperModelResponce response1, retrofit.client.Response response) {

                Log.i(MainActivity.class.getName(), Utils.convertResponseToString(response));

                new Gson().fromJson(Utils.convertResponseToString(response), DesktoperModelResponce.class);
//                Responsemodel = response1.getResponse();
                Responsemodel = new DesktoperModelResponce().getResponse();
                setAdapter();
            }

            @Override
            public void failure(RetrofitError error) {
//                Log.i(MainActivity.class.getName(), error.getResponse().toString());
//                Log.i(MainActivity.class.getName() + "Error", Utils.convertResponseToString(error.getResponse()));

            }
        });*/

        iDesktoper git;

        RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(Constant.BINGWallaper).build();
        git = restAdapter.create(iDesktoper.class);

        git.getWallpaperOfTheDay(new Callback<BingWallpaperModel>() {
            @Override
            public void success(BingWallpaperModel bingWallpaperModel, retrofit.client.Response response) {

                String BingWallpaper = "http://www.bing.com" + bingWallpaperModel.getImages().get(0).getUrl();

                try {
                    Glide.with(MainDrawerActivity.this).load(BingWallpaper).into(WallpaperOfTheDay_img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("Wallpaper", "" + BingWallpaper);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    private void RefreshItem() {

        ++Counter;

        if (Utils.isConnectingToInternet(MainDrawerActivity.this)) {

            mApplication.getGit().getMoreWallpaper(Counter, new Callback<DesktoperModelResponce>() {
                @Override
                public void success(DesktoperModelResponce response1, retrofit.client.Response response) {

                    Log.i(MainActivity.class.getName(), Utils.convertResponseToString(response));

                    Responsemodel.addAll(response1.getResponse());
                    setAdapter();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i(MainActivity.class.getName(), error.getResponse().toString());
                    Log.i(MainActivity.class.getName() + "Error", Utils.convertResponseToString(error.getResponse()));

                }
            });
        }
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void setAdapter() {

        adapter = new CardListAdapater(MainDrawerActivity.this, Responsemodel);
        adapter.setItemclick(this);
        rv_list_card.setAdapter(adapter);
        rv_list_card.setItemAnimator(new DefaultItemAnimator());
        StaggeredGridLayoutManager stagerlayout = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_list_card.setLayoutManager(stagerlayout);
        rv_list_card.scrollToPosition(Responsemodel.size() - 1);

    }

    public void setDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wallpaperoftheaday = (LinearLayout) navigationView.findViewById(R.id.WallpaperOfTheDay_Linear);
        WallpaperOfTheDay_img = (ImageView) navigationView.findViewById(R.id.WallpaperOfTheDay_img);

//        View headerLayout =
//                navigationView.inflateHeaderView(R.layout.nav_header_main_drawer);


        View header = LayoutInflater.from(MainDrawerActivity.this).inflate(R.layout.nav_header_main_drawer, navigationView);
        WallpaperOfTheDay_img = (ImageView) header.findViewById(R.id.WallpaperOfTheDay_img);


        Drawable d = getResources().getDrawable(R.drawable.img_2);
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        Log.d("Bitmap", "" + bitmap);

        if (WallpaperOfTheDay_img != null) {
            WallpaperOfTheDay_img.setImageBitmap(bitmap);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        } else {
            WallpaperOfTheDay_img.setImageBitmap(bitmap);
        }


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_maindrawer_drawer, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_random) {
            // Handle the camera action
            setRandomwallpaper();
        } else if (id == R.id.nav_share) {
            shareApplication();
        } else if (id == R.id.nav_send) {
            shareApplicaitonLink();
        } else if (id == R.id.nav_feedback) {
            sendFeedBack();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void imageItemClicklistioner(int pos) {

        if (actionMode != null) {
            toggleSelection(pos);
        } else {
            startActivity(new Intent(MainDrawerActivity.this, FullscreenWallpaper.class).putExtra("url", Responsemodel.get(pos).getImage().getUrl()));
        }

    }

    @Override
    public void imageItemLongClicklistioner(int pos) {

        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(pos);

    }

    public void setRandomwallpaper() {
        int max = 140, min = 0;

        int randomNum = new Random().nextInt((max - min) + 1) + min;
        if (Utils.isConnectingToInternet(MainDrawerActivity.this)) {

            mApplication.getGit().getMoreWallpaper(randomNum, new Callback<DesktoperModelResponce>() {
                @Override
                public void success(DesktoperModelResponce response1, retrofit.client.Response response) {

                    Log.i(MainActivity.class.getName(), Utils.convertResponseToString(response));

                    Responsemodel.addAll(response1.getResponse());
                    setAdapter();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i(MainActivity.class.getName(), error.getResponse().toString());
                    Log.i(MainActivity.class.getName() + "Error", Utils.convertResponseToString(error.getResponse()));

                }
            });
        }
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void shareApplication() {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("*/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        Uri uri = Uri.parse("/data/apps/" + getApplicationContext().getPackageName() + ".apk");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    public void shareApplicaitonLink() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }


    public void sendFeedBack() {
       /* Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;

//                Toast.makeText(getApplicationContext(), possibleEmail, Toast.LENGTH_SHORT).show();
            }
        }*/

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "siddharthmakadiya@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Valueable FeedBack");
        startActivity(intent);

    }


    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        selectedItemList.add(position);

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    //================================ MULTIPLE ITEM SELECTION ============================

    private class ActionModeCallback implements ActionMode.Callback {
        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MainDrawerActivity.this.getSupportActionBar().hide();
            mode.getMenuInflater().inflate(R.menu.selection_multi, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    // TODO: actually remove items
                    Log.d(TAG, "menu_remove");
                    for (int itemselected : selectedItemList) {
                        adapter.notifyItemRemoved(itemselected);
                    }
//                    adapter.notify();
                    MainDrawerActivity.this.getSupportActionBar().show();
                    mode.finish();
                    return true;

                default:

                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }


}
