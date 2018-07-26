package com.expandablerecyclerviewwebservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ResponseListener,View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    GetListAPI getContactListAPI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        callApi(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }
   /* private void setData() {
        ArrayList<Phone> iphones = new ArrayList<>();
        iphones.add(new Phone("iPhone 4"));
        iphones.add(new Phone("iPhone 4S"));
        iphones.add(new Phone("iPhone 5"));
        iphones.add(new Phone("iPhone 5S"));
        iphones.add(new Phone("iPhone 6"));
        iphones.add(new Phone("iPhone 6Plus"));
        iphones.add(new Phone("iPhone 6S"));
        iphones.add(new Phone("iPhone 6S Plus"));

        ArrayList<Phone> nexus = new ArrayList<>();
        nexus.add(new Phone("Nexus One"));
        nexus.add(new Phone("Nexus S"));
        nexus.add(new Phone("Nexus 4"));
        nexus.add(new Phone("Nexus 5"));
        nexus.add(new Phone("Nexus 6"));
        nexus.add(new Phone("Nexus 5X"));
        nexus.add(new Phone("Nexus 6P"));
        nexus.add(new Phone("Nexus 7"));

        ArrayList<Phone> windowPhones = new ArrayList<>();
        windowPhones.add(new Phone("Nokia Lumia 800"));
        windowPhones.add(new Phone("Nokia Lumia 710"));
        windowPhones.add(new Phone("Nokia Lumia 900"));
        windowPhones.add(new Phone("Nokia Lumia 610"));
        windowPhones.add(new Phone("Nokia Lumia 510"));
        windowPhones.add(new Phone("Nokia Lumia 820"));
        windowPhones.add(new Phone("Nokia Lumia 920"));

        mobileOSes.add(new MobileOS("iOS", iphones));
        mobileOSes.add(new MobileOS("Android", nexus));
        mobileOSes.add(new MobileOS("Window Phone", windowPhones));
    }*/

    private void callApi(int tag) {
        if (Utils.isOnline(
                this)) {

            if (tag == 1) {

                if (getContactListAPI == null)
                    getContactListAPI = new GetListAPI(this, this);
                getContactListAPI.execute();
            }
        } else
            Utils.showToastMessage(this, "Intyernexasdfsfsfqadafsf", false);

    }

    @Override
    public void onResponse(String tag, Const.API_RESULT result, Object obj) {

        if (tag == Const.GET_CONTACT_LIST_API && result == Const.API_RESULT.SUCCESS) {
            try {

                if (result == Const.API_RESULT.SUCCESS) {
                    if (getContactListAPI.mobileOSes == null || getContactListAPI.mobileOSes.size() <= 0) {
                        recyclerView.setVisibility(View.GONE);

                    } else {
                        Log.i("arrrayyyyyyyyy", String.valueOf(getContactListAPI.mobileOSes.size()));
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new RecyclerAdapter(this, getContactListAPI.mobileOSes);
                        recyclerView.setAdapter(adapter);
                    }
                } else {

                    recyclerView.setVisibility(View.GONE);

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
