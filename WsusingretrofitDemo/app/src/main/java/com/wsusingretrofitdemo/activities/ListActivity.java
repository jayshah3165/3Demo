package com.wsusingretrofitdemo.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.adapters.GetListAdapter;
import com.wsusingretrofitdemo.api.DeleteListAPI;
import com.wsusingretrofitdemo.api.GetListAPI;
import com.wsusingretrofitdemo.backend.ResponseListener;
import com.wsusingretrofitdemo.custom.CustomHeader;
import com.wsusingretrofitdemo.utils.Const;
import com.wsusingretrofitdemo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity implements ResponseListener, View.OnClickListener {

    @BindView(R.id.header)
    CustomHeader header;
    @BindView(R.id.listiew)
    RecyclerView listiew;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.txtNoDataFound)
    TextView txtNoDataFound;

    GetListAPI getListAPI = null;
    GetListAdapter getListAdapter = null;
    DeleteListAPI deleteListAPI = null;
    private int lastPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        Utils.applyFontFace(this, this.findViewById(android.R.id.content).getRootView());
        init();
    }

    private void init() {
        header.imgLeft.setVisibility(View.VISIBLE);
        header.imgLeft.setImageResource(R.drawable.back_arrow);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listiew.setLayoutManager(layoutManager);
        getListAdapter = new GetListAdapter(ListActivity.this);
        listiew.setAdapter(getListAdapter);

        callApi(1);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callApi(1);
            }
        });

    }

    private void callApi(int tag) {
        if (Utils.isOnline(this)) {
            Utils.showProgress(this);
            if (tag == 1) {
                if (getListAPI == null)
                    getListAPI = new GetListAPI(this, this);
                getListAPI.execute();
            } else if (tag == 2) {
                if (deleteListAPI == null)
                    deleteListAPI = new DeleteListAPI(this, this);
                deleteListAPI.execute(getListAdapter.objList.get(lastPos).id);
            }
        } else
            Utils.showToastMessage(this, getResources().getString(R.string.checkInternet), false);

    }

    @Override
    public void onResponse(String tag, Const.API_RESULT result, Object obj) {
        Utils.dismissProgress();
        if (tag == Const.LIST_API) {
            try {
                swipeContainer.setRefreshing(false);
                if (result == Const.API_RESULT.SUCCESS) {
                    if (getListAPI.objList == null || getListAPI.objList.size() <= 0) {
                        listiew.setVisibility(View.GONE);
                        txtNoDataFound.setVisibility(View.VISIBLE);
                    } else {
                        txtNoDataFound.setVisibility(View.GONE);
                        listiew.setVisibility(View.VISIBLE);
                        getListAdapter.addData(getListAPI.objList);
                    }
                } else {
                    swipeContainer.setRefreshing(false);
                    listiew.setVisibility(View.GONE);
                    txtNoDataFound.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (tag == Const.DELETE_API && result == Const.API_RESULT.SUCCESS) {
            getListAdapter.objList.remove(lastPos);
            getListAdapter.notifyDataSetChanged();
            callApi(1);
        }
    }


    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.row:
                lastPos = Integer.parseInt(view.getTag().toString());
                break;
            case R.id.btnDelete:
                lastPos = Integer.parseInt(view.getTag().toString());
                callApi(2);
                break;
        }
    }


}
