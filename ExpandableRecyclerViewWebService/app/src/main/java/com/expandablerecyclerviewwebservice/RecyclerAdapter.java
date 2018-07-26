package com.expandablerecyclerviewwebservice;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EchoIT on 7/26/2018.
 */

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<OSViewHolder, PhoneViewHolder> {

    private Activity activity;
    public RecyclerAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public OSViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new OSViewHolder(view);
    }

    @Override
    public PhoneViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);

        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(PhoneViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Phone phone = ((MobileOS) group).getItems().get(childIndex);
        holder.onBind(phone,group);
    }

    @Override
    public void onBindGroupViewHolder(OSViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
