package com.lelocab.rides;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.lelocab.CompleteRideDetail.CompleteRideDetailFragment;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.baseclasses.BaseActivity;

import java.util.ArrayList;

/**
 * Created by ashish on 09-05-2017.
 */

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.ViewHolder> {
    private BaseActivity mActivity;
    private ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels;

    public RidesAdapter(BaseActivity mActivity, ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels) {
        this.mActivity = mActivity;
        this.getAllCompleteRideHistoryResponseModels = getAllCompleteRideHistoryResponseModels;
    }

    @Override
    public RidesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rides_row, parent, false);

        return new RidesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RidesAdapter.ViewHolder holder, final int position) {
        holder.getBinding().setVariable(BR.dto, getAllCompleteRideHistoryResponseModels.get(position));
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FROM, RidesAdapter.class.getName());
                mActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, CompleteRideDetailFragment.newInstance(getAllCompleteRideHistoryResponseModels.get(position), bundle))
                        .addToBackStack(CompleteRideDetailFragment.class.getName())
                        .commit();
            }
        });
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return getAllCompleteRideHistoryResponseModels.size();
    }

    public void changeData(ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels) {
        this.getAllCompleteRideHistoryResponseModels = getAllCompleteRideHistoryResponseModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
        }


        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
