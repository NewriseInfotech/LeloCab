package com.lelocab.ridedetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lelocab.CompleteRideDetail.CompleteRideDetailFragment;
import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.databinding.RideDetailFragmentBinding;
import com.lelocab.notification.NotificationResponseModel;
import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;

/**
 * Created by ashish on 25-04-2017.
 */

public class RideDetailFragment extends Fragment implements IRideDetailView {

    private RideDetailFragmentBinding binding;
    private MainActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ride_details, container, false);
        mActivity = (MainActivity) getActivity();
        mActivity.displayHomeButton(false);
        return binding.getRoot();
    }


    public static RideDetailFragment newInstance(NotificationResponseModel notificationResponseModel) {
        RideDetailFragment rideDetailFragment = new RideDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(NotificationResponseModel.class.getName(), notificationResponseModel);
        rideDetailFragment.setArguments(bundle);
        return rideDetailFragment;
    }

    @Override
    public void onCompleteRideDetail(GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel) {
        Bundle bundle = new Bundle();
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, CompleteRideDetailFragment.newInstance(getAllCompleteRideHistoryResponseModel, bundle))
                .commit();
    }
}
