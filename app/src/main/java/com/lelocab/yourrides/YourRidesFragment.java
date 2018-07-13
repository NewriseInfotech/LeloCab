package com.lelocab.yourrides;

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
import com.lelocab.databinding.BookingSummaryFragmentBinding;
import com.lelocab.databinding.YourRidesFragmentBinding;

/**
 * Created by ashish on 25-04-2017.
 */

public class YourRidesFragment extends Fragment {
    private YourRidesFragmentBinding binding;
    private MainActivity mActivity;
    private YourRidesAdapter yourRidesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.your_rides_fragment, container, false);
        mActivity = (MainActivity) getActivity();
        mActivity.displayHomeButton(true);
        mActivity.setTitle(getString(R.string.your_rides));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        yourRidesAdapter = new YourRidesAdapter(mActivity);
        binding.recyclerView.setAdapter(yourRidesAdapter);
    }
}
