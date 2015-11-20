package com.pemikir.desktopper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.pemikir.desktopper.DesktoperAPP;
import com.pemikir.desktopper.Model.DesktoperModelResponce;
import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Utility.Utils;
import com.pemikir.desktopper.adapater.CardListAdapater;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements CardListAdapater.itemClickLister {

    DesktoperAPP mApplication;
    RecyclerView rv_list_card;
    CardListAdapater adapter;
    int Counter = 0;
    List<com.pemikir.desktopper.Model.Response> Responsemodel;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApplication = (DesktoperAPP) getApplicationContext();

        init();


    }

    private void init() {

        rv_list_card = (RecyclerView) findViewById(R.id.recycler_view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                RefreshItem();
            }
        });

        mApplication.getGit().getAllWallpaper(new Callback<DesktoperModelResponce>() {
            @Override
            public void success(DesktoperModelResponce response1, Response response) {

                Log.i(MainActivity.class.getName(), Utils.convertResponseToString(response));

                Responsemodel = response1.getResponse();
                setAdapter();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i(MainActivity.class.getName(), error.getResponse().toString());
                Log.i(MainActivity.class.getName() + "Error", Utils.convertResponseToString(error.getResponse()));

            }
        });

    }

    private void RefreshItem() {

        ++Counter;

        if (Utils.isConnectingToInternet(MainActivity.this)) {

            mApplication.getGit().getMoreWallpaper(Counter, new Callback<DesktoperModelResponce>() {
                @Override
                public void success(DesktoperModelResponce response1, Response response) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mMenu = menu;

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);
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

    public void setAdapter() {
        adapter = new CardListAdapater(MainActivity.this, Responsemodel);
        adapter.setItemclick(this);
        rv_list_card.setAdapter(adapter);
        rv_list_card.setItemAnimator(new DefaultItemAnimator());
        StaggeredGridLayoutManager stagerlayout = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_list_card.setLayoutManager(stagerlayout);
        rv_list_card.scrollToPosition(Responsemodel.size() - 1);

    }

    @Override
    public void imageItemClicklistioner(int pos) {

        if (actionMode != null) {
            toggleSelection(pos);
        } else {
            startActivity(new Intent(MainActivity.this, FullscreenWallpaper.class).putExtra("url", Responsemodel.get(pos).getImage().getUrl()));
        }

    }

    @Override
    public void imageItemLongClicklistioner(int pos) {

        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(pos);

    }


    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

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
            MainActivity.this.getSupportActionBar().hide();
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

                    mode.finish();
                    return true;

                default:
                    MainActivity.this.getSupportActionBar().show();
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
