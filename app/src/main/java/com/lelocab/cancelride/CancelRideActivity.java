package com.lelocab.cancelride;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.lelocab.R;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.CancelRideDataBinding;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.ridedetail.RideInfoModel;

/**
 * Created by ashish on 24-05-2017.
 */

public class CancelRideActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ICancelRideView {
    CancelRideDataBinding binding;
    private CancelRideRequestModel cancelRideRequestModel;
    private Toolbar toolbar;
    private RideInfoModel rideInfoModel;
    private long RideId;
    private LoginResponseModel loginResponseModel;
    private ICancelRidePresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.cancel_ride_activity);
        cancelRideRequestModel = new CancelRideRequestModel();
        rideInfoModel = (RideInfoModel) getIntent().getSerializableExtra(RideInfoModel.class.getName());
        loginResponseModel = Prefs.getObjectFromPref(this, LoginResponseModel.class.getName());
        presenter = new CancelRidePresenterImpl(this, this);
        RideId = getIntent().getLongExtra("RideId", 0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.cancel_ride));
        binding.radioGroup.setOnCheckedChangeListener(this);
    }

    public void onClickSubmit(View view) {
        cancelRideRequestModel.setDriverId(rideInfoModel.getDriverId());
        cancelRideRequestModel.setUser(true);
        cancelRideRequestModel.setRideId(RideId);
        cancelRideRequestModel.setUserId(loginResponseModel.getId());
        presenter.CancelRide(cancelRideRequestModel);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        binding.edReason.setVisibility(View.GONE);
        switch (i) {
            case R.id.rb_one:
                cancelRideRequestModel.setCancelReason(1);
                break;
            case R.id.rb_two:
                cancelRideRequestModel.setCancelReason(2);
                break;
            case R.id.rb_three:
                cancelRideRequestModel.setCancelReason(3);
                break;
            case R.id.rb_four:
                cancelRideRequestModel.setCancelReason(4);
                break;
            case R.id.rb_five:
                cancelRideRequestModel.setCancelReason(5);
                break;
            case R.id.rb_six:
                cancelRideRequestModel.setCancelReason(6);
                binding.edReason.setVisibility(View.VISIBLE);
                break;
        }
    }

}
