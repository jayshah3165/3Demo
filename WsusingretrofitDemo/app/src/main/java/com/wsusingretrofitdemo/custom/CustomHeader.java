package com.wsusingretrofitdemo.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CustomHeader extends RelativeLayout implements View.OnClickListener {
    public Context objContext;
    View view = null;
    @BindView(R.id.imgLeft)
    public ImageView imgLeft;
    @BindView(R.id.txtCenterTitle)
    public TextView txtCenterTitle;
    @BindView(R.id.imgRight)
    public ImageView imgRight;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private InputMethodManager imm = null;

    public CustomHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        objContext = context;
        init();
    }

    public CustomHeader(final Context context) {
        super(context);
        objContext = context;
        Utils.applyFontFace(objContext, this.findViewById(android.R.id.content).getRootView());
        init();

    }


    public void init() {
        view = LayoutInflater.from(objContext).inflate(R.layout.cust_header, this, true);
        Utils.applyFontFace(objContext, view);
        ButterKnife.bind(this, view);
        imm = (InputMethodManager) objContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @OnClick({R.id.imgLeft})
    public void onClick(View view) {
        Utils.hideKeyBoard(objContext, view);
        switch (view.getId()) {
            case R.id.imgLeft:
                ((Activity) objContext).finish();
                ((Activity) objContext).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }

}
