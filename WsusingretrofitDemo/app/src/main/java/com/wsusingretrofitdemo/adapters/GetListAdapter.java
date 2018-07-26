package com.wsusingretrofitdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.model.ListModel;
import com.wsusingretrofitdemo.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GetListAdapter extends RecyclerView.Adapter<GetListAdapter.MyViewHolder> {
    Context context;

    private LayoutInflater inflater = null;
    public ArrayList<ListModel> objList = null;

    public GetListAdapter(Context context) {
        this.context = context;
        objList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list, parent, false);
        Utils.applyFontFace(context, itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {

        Utils.print("SIzeeeeeeeeeeee===================" + objList.size());
        return objList.size();
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public void addData(ArrayList<ListModel> mobjList) {

        objList = new ArrayList<>();
        objList.addAll(mobjList);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // TODO Auto-generated method stub
        holder.txtText.setText(objList.get(position).title);

        holder.row.setTag(position);
        holder.row.setOnClickListener((View.OnClickListener) context);

        holder.btnDelete.setTag(position);
        holder.btnDelete.setOnClickListener((View.OnClickListener) context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtText)
        TextView txtText;
        @BindView(R.id.row)
        LinearLayout row;
        @BindView(R.id.btnDelete)
        ImageView btnDelete;

        public MyViewHolder(View convertView) {
            super(convertView);
            ButterKnife.bind(this, convertView);
        }
    }


}