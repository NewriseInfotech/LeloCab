package com.lelocab.rating;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lelocab.R;
import com.lelocab.databinding.RatingFragmentDataBinding;

/**
 * Created by ashish on 15-05-2017.
 */

public class RatingFragment extends Fragment {
    private RatingFragmentDataBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rating_fragment, container, false);

        return binding.getRoot();
    }



}
