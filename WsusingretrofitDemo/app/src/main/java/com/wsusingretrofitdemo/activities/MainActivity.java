package com.wsusingretrofitdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.custom.CustomHeader;
import com.wsusingretrofitdemo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.header)
    CustomHeader header;
    @BindView(R.id.txtHome)
    TextView txtHome;
    @BindView(R.id.txtLogout)
    TextView txtLogout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.listiew)
    RecyclerView listiew;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Utils.applyFontFace(this, this.findViewById(android.R.id.content).getRootView());
        init();
    }

    private void init() {

        header.txtCenterTitle.setText("DEMO");
        header.txtCenterTitle.setVisibility(View.VISIBLE);
        header.imgLeft.setVisibility(View.VISIBLE);
        header.imgLeft.setImageResource(R.drawable.menu);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.Open, R.string.Close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Utils.closeAllScreens();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;

        Utils.showToastMessage(this, getResources().getString(R.string.appExitMessage), true);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @OnClick({R.id.imgLeft, R.id.txtLogout, R.id.txtHome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgLeft:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.txtLogout:
                drawer.closeDrawers();
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;

            case R.id.txtHome:
                drawer.closeDrawers();
                break;
        }
    }


}
