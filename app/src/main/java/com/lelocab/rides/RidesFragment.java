package com.lelocab.rides;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.databinding.EarningsFragmentBinding;

import java.util.ArrayList;

/**
 * Created by ashish on 08-05-2017.
 */

public class RidesFragment extends Fragment implements IRidesView {
    private EarningsFragmentBinding binding;
    private MainActivity mActivity;
    private RidesAdapter adapter;
    private IRidesPresenter presenter;
    private ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rides_fragment, container, false);
        mActivity = (MainActivity) getActivity();
        adapter = new RidesAdapter(mActivity, getAllCompleteRideHistoryResponseModels);
        presenter = new RidesPresenterImpl(mActivity, this);
        mActivity.displayHomeButton(true);
        mActivity.setTitle(mActivity.getString(R.string.your_rides));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        presenter.getAllCompleteRideHistory();
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onGetAllCompleteRideHistory(ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels) {
        adapter.changeData(getAllCompleteRideHistoryResponseModels);
        adapter.notifyDataSetChanged();
    }
}
