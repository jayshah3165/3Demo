package com.wsusingretrofitdemo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wsusingretrofitdemo.R;
import com.wsusingretrofitdemo.api.SignupAPI;
import com.wsusingretrofitdemo.backend.ResponseListener;
import com.wsusingretrofitdemo.custom.CustomHeader;
import com.wsusingretrofitdemo.model.UserModel;
import com.wsusingretrofitdemo.utils.Const;
import com.wsusingretrofitdemo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity implements ResponseListener {

    @BindView(R.id.edtNAme)
    EditText edtNAme;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignin)
    Button btnSignin;
    @BindView(R.id.header)
    CustomHeader header;

    UserModel userModel = null;
    SignupAPI signupAPI = null;
    @BindView(R.id.txtDob)
    TextView txtDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        Utils.applyFontFace(this, this.findViewById(android.R.id.content).getRootView());
        init();
    }


    private void saveValue() {
        if (userModel == null)
            userModel = new UserModel();
        userModel.name = Utils.getTextValue(edtNAme);
        userModel.email = Utils.getTextValue(edtEmail);
        userModel.birthdate = Utils.convertDateStringToString(Utils.getTextValue(txtDob),"dd-MM-yyyy","yyyy-MM-dd");
//        userModel.profile_pic = selectedImagePath;
    }

    private void init() {
        header.txtCenterTitle.setText("REGISTERATION");
        header.txtCenterTitle.setVisibility(View.VISIBLE);
    }

    private boolean isValidate() {
        boolean isError = false;
        if (Utils.isEmptyText(edtNAme)) {
            edtNAme.requestFocus();
            edtNAme.setError(String.format(getResources().getString(R.string.Please_enter_value), getResources().getString(R.string.Name).toLowerCase()));
            isError = true;
        } else if (Utils.isEmptyText(edtPhone)) {
            edtPhone.requestFocus();
            edtPhone.setError(String.format(getResources().getString(R.string.Please_enter_value), getResources().getString(R.string.Phone).toLowerCase()));
            isError = true;
        } else if (Utils.getTextValue(edtPhone).trim().length() < 8 || Utils.getTextValue(edtPhone).trim().length() > 15) {
            Utils.showToastMessage(RegistrationActivity.this, getResources().getString(R.string.PleaseEnterValidMobile), true);
            edtPhone.requestFocus();
            isError = true;
        } else if (Utils.isEmptyText(edtEmail)) {
            edtEmail.requestFocus();
            edtEmail.setError(String.format(getResources().getString(R.string.Please_enter_value), getResources().getString(R.string.Email).toLowerCase()));
            isError = true;
        } else if (!Utils.isValidEmail(Utils.getTextValue(edtEmail))) {
            edtEmail.requestFocus();
            edtEmail.setError(String.format(getResources().getString(R.string.Please_enter_valid_value), getResources().getString(R.string.Email).toLowerCase()));
            isError = true;
        } else if (Utils.isEmptyText(edtPassword)) {
            edtPhone.requestFocus();
            edtPhone.setError(String.format(getResources().getString(R.string.Please_enter_value), getResources().getString(R.string.Password).toLowerCase()));
            isError = true;
        } else if (Utils.getTextValue(edtPassword).trim().length() < 6) {
            edtPassword.requestFocus();
            edtPassword.setError(String.format(getResources().getString(R.string.Please_enter_valid_password_value), getResources().getString(R.string.Password).toLowerCase()));
            isError = true;
        }
//        else if (!chkTermsCondition.isChecked()) {
//            Utils.showToastMessage(RegistrationActivity.this, getResources().getString(R.string.please_accept_terms_conditions), true);
//            isError = true;
//        }
        return isError;
    }


    @Override
    public void onResponse(String tag, Const.API_RESULT result, Object obj) {
        Utils.dismissProgress();
        if (tag == Const.USER_REGISTRATION_API && result == Const.API_RESULT.SUCCESS) {
//            startActivity(new Intent(RegistrationActivity.this, OtpActivity.class).putExtra("IS_SOCIAL",isSocailLogin).putExtra("IS_DRIVER", true));
//            finish();
        }

    }

    private void callApi(int tag) {
        if (Utils.isOnline(this)) {
            Utils.showProgress(this);
            if (tag == 1) {
                if (signupAPI == null)
                    signupAPI = new SignupAPI(this, this);
                signupAPI.execute(userModel);
            }
        } else {
            Utils.showToastMessage(this, getResources().getString(R.string.checkInternet), false);
        }
    }


    @OnClick({R.id.btnSignin,R.id.txtDob})
    public void onClick(View view) {
        Utils.hideKeyBoard(RegistrationActivity.this, view);
        switch (view.getId()) {
            case R.id.btnSignin:
                if (!isValidate()) {
                    saveValue();
//                    if (selectedImagePath == null || selectedImagePath.isEmpty())
//                        callApi(1);
//                    else
//                        callApi(2);
                }
                break;

            case R.id.txtDob:
                txtDob.setFocusable(true);
                Utils.setDateTimeField(this, txtDob);
                break;
        }
    }

}
