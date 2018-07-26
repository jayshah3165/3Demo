package com.langaugechangedemo;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.llUser)
    LinearLayout llUser;

    @BindView(R.id.llDriver)
    LinearLayout llDriver;
    @BindView(R.id.txtEnglish)
    TextView txtEnglish;
    @BindView(R.id.txtChinese)
    TextView txtChinese;
    @BindView(R.id.txtFrench)
    TextView txtFrench;
    @BindView(R.id.txtSpenish)
    TextView txtSpenish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
    }

    private void initialization() {

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (Pref.getStringValue(MainActivity.this, Const.PREF_LANGUAGE, Const.EN).equalsIgnoreCase(Const.EN)){
            txtEnglish.setBackgroundResource(R.drawable.rounded_corner_solid_blue_bg);
            txtEnglish.setTextColor(getResources().getColor(R.color.white));

            txtChinese.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtChinese.setTextColor(getResources().getColor(R.color.headercolour));

            txtFrench.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtFrench.setTextColor(getResources().getColor(R.color.headercolour));

            txtSpenish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtSpenish.setTextColor(getResources().getColor(R.color.headercolour));
        }else if (Pref.getStringValue(MainActivity.this, Const.PREF_LANGUAGE, Const.ZH).equalsIgnoreCase(Const.ZH)){
            txtEnglish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtEnglish.setTextColor(getResources().getColor(R.color.headercolour));

            txtChinese.setBackgroundResource(R.drawable.rounded_corner_solid_blue_bg);
            txtChinese.setTextColor(getResources().getColor(R.color.white));

            txtFrench.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtFrench.setTextColor(getResources().getColor(R.color.headercolour));

            txtSpenish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtSpenish.setTextColor(getResources().getColor(R.color.headercolour));
        }else if (Pref.getStringValue(MainActivity.this, Const.PREF_LANGUAGE, Const.FR).equalsIgnoreCase(Const.FR)){
            txtEnglish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtEnglish.setTextColor(getResources().getColor(R.color.headercolour));

            txtChinese.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtChinese.setTextColor(getResources().getColor(R.color.headercolour));

            txtFrench.setBackgroundResource(R.drawable.rounded_corner_solid_blue_bg);
            txtFrench.setTextColor(getResources().getColor(R.color.white));

            txtSpenish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtSpenish.setTextColor(getResources().getColor(R.color.headercolour));
        }else if (Pref.getStringValue(MainActivity.this, Const.PREF_LANGUAGE, Const.ES).equalsIgnoreCase(Const.ES)){
            txtEnglish.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtEnglish.setTextColor(getResources().getColor(R.color.headercolour));

            txtChinese.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtChinese.setTextColor(getResources().getColor(R.color.headercolour));

            txtFrench.setBackgroundResource(R.drawable.rounded_corner_solid_white_with_black_bg);
            txtFrench.setTextColor(getResources().getColor(R.color.headercolour));

            txtSpenish.setBackgroundResource(R.drawable.rounded_corner_solid_blue_bg);
            txtSpenish.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @OnClick({R.id.llUser, R.id.llDriver, R.id.txtEnglish, R.id.txtChinese,R.id.txtFrench,R.id.txtSpenish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDriver:
                break;

            case R.id.llUser:
                break;

            case R.id.txtEnglish:
                changeLanguage(0);
                break;
            case R.id.txtChinese:
                changeLanguage(1);
                break;
            case R.id.txtFrench:
                changeLanguage(2);
                break;

            case R.id.txtSpenish:
                changeLanguage(3);
                break;

        }

    }

    public void changeLanguage(int selected)
    {
        if (selected==0){
            Pref.setStringValue(MainActivity.this, Const.PREF_LANGUAGE,Const.EN);
        }else if (selected==1){
            Pref.setStringValue(MainActivity.this, Const.PREF_LANGUAGE,Const.ZH);
        }else if (selected==2){
            Pref.setStringValue(MainActivity.this, Const.PREF_LANGUAGE,Const.FR);
        }else if (selected==3){
            Pref.setStringValue(MainActivity.this, Const.PREF_LANGUAGE,Const.ES);
        }
        Utils.setLocale(getApplicationContext(), Pref.getStringValue(MainActivity.this, Const.PREF_LANGUAGE, Const.EN));
        this.recreate();

    }
}