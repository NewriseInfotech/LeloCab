package com.lelocab.home.processjob;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.CallbackAlertDialog;
import com.lelocab.Utills.SimpleAlertDialog;
import com.lelocab.driverdetail.DriverDetailFragment;
import com.lelocab.custom.ProgressWheel;
import com.lelocab.databinding.ProcessJobFragmentBinding;
import com.lelocab.ridedetail.RideInfoModel;

/**
 * Created by ashish on 05-05-2017.
 */

public class ProcessJobFragment extends Fragment implements IProcessJobView {
    private ProcessJobFragmentBinding binding;
    private MainActivity mActivity;
    private ProgressWheel pwOne;
    private IProcessJobPresenter iProcessJobPresenter;
    private long RideId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.process_request_fragment, container, false);
        mActivity = (MainActivity) getActivity();
        mActivity.displayHomeButton(false);
        RideId = getArguments().getLong("RideId");
        initializeView(binding);
        return binding.getRoot();
    }

    private void initializeView(ProcessJobFragmentBinding binding) {
        mActivity.setTitle(getString(R.string.process_job_title));
        iProcessJobPresenter = new ProcessJobPresenterImpl(mActivity);
        iProcessJobPresenter.setProcessView(this);
        pwOne = binding.progressBar;
        pwOne.setProgress(0);
        iProcessJobPresenter.startJobSearching(RideId);
    }

    @Override
    public void onProgressChanged(double percent) {
        double progress = 360.0 * (percent / 100.0);
        pwOne.setProgress((int) progress);
        pwOne.setText((int) percent + "%");
    }

    @Override
    public void onSearchCompleteFaluire(int loop) {
        if (loop < 2) {
            new SimpleAlertDialog(mActivity, false, getString(R.string.app_name), "None of the drivers has accepted your job\n" +
                    "\n" +
                    "Do you want to wait for another minute or cancel your booking?", "Wait", "Cancel", new CallbackAlertDialog() {
                @Override
                public void onClickAlertBtn(AlertDialog dialog) {
                    iProcessJobPresenter.startJobSearching(RideId);
                    dialog.dismiss();
                }
            }, new CallbackAlertDialog() {
                @Override
                public void onClickAlertBtn(AlertDialog dialog) {
                    mActivity.onBackPressed();
                }
            }).show();
        } else {
            new SimpleAlertDialog(mActivity, false, getString(R.string.app_name), "No Driver found.We are cancelling your Job.", "OK", null, new CallbackAlertDialog() {
                @Override
                public void onClickAlertBtn(AlertDialog dialog) {
                    dialog.dismiss();
                    mActivity.onBackPressed();
                }
            }, null).show();
        }
    }

    @Override
    public void onAddJobSuccess(long id) {

    }

    @Override
    public void onRideAccepted(RideInfoModel rideInfoModel) {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, DriverDetailFragment.newInstance(rideInfoModel, RideId))
                .commit();
    }

    public static ProcessJobFragment newInstance(long rideID) {
        ProcessJobFragment processJobFragment = new ProcessJobFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("RideId", rideID);
        processJobFragment.setArguments(bundle);
        return processJobFragment;
    }
}
