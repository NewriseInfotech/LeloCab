package com.lelocab.forgotpassword;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lelocab.R;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.ForgotPasswordActivityBinding;

/**
 * Created by ashish on 24-04-2017.
 */

public class ForgotPasswordActivity extends BaseActivity implements IForgotPasswordView {

    private ForgotPasswordActivityBinding binding;
    private Toolbar toolbar;
    private IForgotPasswordPresenter presenter;
    private ForgotPasswordRequestModel forgotPasswordRequestModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.forgot_password_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        presenter = new ForgotPasswordPresenterImpl(binding, this, this);
        forgotPasswordRequestModel = new ForgotPasswordRequestModel();
        setSupportActionBar(toolbar);
        binding.setPresenter(presenter);
        binding.setDto(forgotPasswordRequestModel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onPasswordSuccess(String message) {

    }
}
