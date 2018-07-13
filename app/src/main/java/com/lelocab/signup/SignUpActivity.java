package com.lelocab.signup;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.SignUpActivityBinding;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.verification.VerificationActivity;

/**
 * Created by ashish on 21-04-2017.
 */

public class SignUpActivity extends BaseActivity implements ISignUpView {
    private SignUpActivityBinding binding;
    private Toolbar toolbar;
    private ISignUpPresenter presenter;
    private SignUpRequestModel signUpRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signUpRequestModel = new SignUpRequestModel();
        presenter = new SignUpPresenterImpl(binding, this, this);
        setSupportActionBar(toolbar);
        binding.setSignUpRequestModel(signUpRequestModel);
        binding.setPresenter(presenter);
        toolbar.setTitle(getString(R.string.sign_up));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSignUp(LoginResponseModel loginResponseModel) {
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.putExtra(LoginResponseModel.class.getName(), loginResponseModel);
        startActivity(intent);
    }

}
