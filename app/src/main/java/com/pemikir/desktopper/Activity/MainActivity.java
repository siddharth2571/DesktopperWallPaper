package com.pemikir.desktopper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.pemikir.desktopper.DesktoperAPP;
import com.pemikir.desktopper.Model.DesktoperModelResponce;
import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Utility.Utils;
import com.pemikir.desktopper.adapater.CardListAdapater;
import com.pemikir.desktopper.multi_selection_adapter.RecyclerViewDemoAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements CardListAdapater.itemClickLister, RecyclerView.OnItemTouchListener,
        View.OnClickListener,
        ActionMode.Callback {

    DesktoperAPP mApplication;
    RecyclerView rv_list_card;
    //    CardListAdapater adapter;
    RecyclerViewDemoAdapter adapter;
    GestureDetectorCompat gestureDetector;

    int Counter = 0;
    List<com.pemikir.desktopper.Model.Response> Responsemodel;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //    private ActionModeCallback actionModeCallback = new ActionModeCallback();
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

        gestureDetector =
                new GestureDetectorCompat(this, new RecyclerViewDemoOnGestureListener());

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
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.selection_multi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_settings) {
//            removeItemFromList();
        }
        return true;
    }


    public void setAdapter() {
        adapter = new RecyclerViewDemoAdapter(Responsemodel);
//        adapter.setItemclick(this);
        rv_list_card.setAdapter(adapter);
        rv_list_card.setItemAnimator(new DefaultItemAnimator());
        StaggeredGridLayoutManager stagerlayout = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_list_card.setLayoutManager(stagerlayout);
        rv_list_card.scrollToPosition(Responsemodel.size() - 1);

    }

    @Override
    public void imageItemClicklistioner(View view, int pos) {

        if (actionMode != null) {
            toggleSelection(pos);
        } else {
            startActivity(new Intent(MainActivity.this, FullscreenWallpaper.class).putExtra("url", Responsemodel.get(pos).getImage().getUrl()));
        }

    }

    @Override
    public void imageItemLongClicklistioner(View view, int pos) {

        if (actionMode == null) {
//            actionMode = startSupportActionMode(actionModeCallback);
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

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        String title = getString(R.string.app_name, adapter.getSelectedItemCount());
        actionMode.setTitle(title);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.selection_multi, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_delete:
                List<Integer> selectedItemPositions = adapter.getSelectedItems();
                int currPos;
                for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                    currPos = selectedItemPositions.get(i);
                    adapter.removeData(currPos);
                    Log.i("Adapter_Selcted", "" + currPos);
                }

                actionMode.finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {
        this.actionMode = null;
        adapter.clearSelections();

    }

    @Override
    public void onClick(View v) {

    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = rv_list_card.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = rv_list_card.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            // Start the CAB using the ActionMode.Callback defined above
//            actionMode = startActionMode(MainActivity.this);
            int idx = rv_list_card.getChildPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }
}

//================================ MULTIPLE ITEM SELECTION ============================

   /* private class ActionModeCallback implements ActionMode.Callback {
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


}*/
