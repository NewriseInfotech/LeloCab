package com.lelocab.driverdetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.Prefs;
import com.lelocab.databinding.BookingSummaryFragmentBinding;
import com.lelocab.login.LoginActivity;
import com.lelocab.login.LoginRequestModel;
import com.lelocab.ridedetail.RideInfoModel;
import com.squareup.picasso.Picasso;

/**
 * Created by ashish on 23-04-2017.
 */

public class DriverDetailFragment extends Fragment implements IDriverDetailView {
    private BookingSummaryFragmentBinding binding;
    private MainActivity mActivity;
    private RideInfoModel rideInfoModel;
    private IDriverDetailPresenter presenter;
    private long RideId;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.driver_detail_fragment, container, false);
        binding.setDriverDetailFragment(this);
        mActivity = (MainActivity) getActivity();
        presenter = new DriverDetailPresenterImpl(mActivity, this);
        rideInfoModel = (RideInfoModel) getArguments().getSerializable(RideInfoModel.class.getName());
        RideId = getArguments().getLong("RideId");
        Picasso.with(mActivity)
                .load(rideInfoModel.getDriverPicture())
                .into(binding.profileImage);
        binding.setRideInfoModel(rideInfoModel);
        mActivity.setTitle(getString(R.string.driver_details));
        mActivity.displayHomeButton(false);
        return binding.getRoot();
    }

    public void onFinishClick(View view) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        mActivity.finish();
    }

    public void onCancelClick(View view) {
        new AlertDialog.Builder(mActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure you want to cancel?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.cancelRide(rideInfoModel, RideId);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public static DriverDetailFragment newInstance(RideInfoModel rideInfoModel, long rideId) {
        DriverDetailFragment driverDetailFragment = new DriverDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RideInfoModel.class.getName(), rideInfoModel);
        bundle.putLong("RideId", rideId);
        driverDetailFragment.setArguments(bundle);
        return driverDetailFragment;
    }

    @Override
    public void onCancelRide() {

    }
}