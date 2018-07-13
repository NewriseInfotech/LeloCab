package com.lelocab.verification;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.VerificationFragmentDataBinding;
import com.lelocab.login.LoginResponseModel;

/**
 * Created by ashish on 20-05-2017.
 */

public class VerificationActivity extends BaseActivity implements IVerificationView {
    private VerificationFragmentDataBinding binding;
    private LoginResponseModel loginResponseModel;
    private IVerificationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.verification_activity);
        loginResponseModel = (LoginResponseModel) getIntent().getSerializableExtra(LoginResponseModel.class.getName());
        presenter = new VerificationPresenterImpl(this, this);
        binding.setPresenter(presenter);
        binding.setLoginResponseModel(loginResponseModel);
        binding.setOtp("");
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle(R.string.verification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onVerifyUser(LoginResponseModel loginResponseModel) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onResendOtp(LoginResponseModel loginResponseModel) {
        this.loginResponseModel = loginResponseModel;
        binding.setLoginResponseModel(loginResponseModel);
    }
}
