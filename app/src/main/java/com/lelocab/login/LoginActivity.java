package com.lelocab.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.LoginActivityBinding;
import com.lelocab.forgotpassword.ForgotPasswordActivity;
import com.lelocab.signup.SignUpActivity;

/**
 * Created by ashish on 21-04-2017.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    private LoginActivityBinding binding;
    private Toolbar toolbar;
    private LoginRequestModel loginRequestModel;
    private ILoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        FirebaseInstanceId.getInstance().getToken();
        if (Prefs.getObjectFromPref(this, LoginRequestModel.class.getName()) != null) {
            loginRequestModel = Prefs.getObjectFromPref(this, LoginRequestModel.class.getName());
        } else {
            loginRequestModel = new LoginRequestModel();
        }
        presenter = new LoginPresenterImpl(binding, this, this);
        binding.setLoginRequestModel(loginRequestModel);
        presenter.onLoginClick(loginRequestModel);
        binding.setPresenter(presenter);
    }

    public void onClickSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onClickForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLogin(LoginResponseModel loginResponseModel) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
