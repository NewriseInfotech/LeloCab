package com.lelocab.yourrides;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.driverdetail.DriverDetailFragment;
import com.lelocab.ridedetail.RideDetailFragment;

/**
 * Created by ashish on 25-04-2017.
 */
public class YourRidesAdapter extends RecyclerView.Adapter<YourRidesAdapter.ViewHolder> {
    MainActivity mActivity;

    public YourRidesAdapter(MainActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.your_ride_row, parent, false);

        return new YourRidesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().executePendingBindings();
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, new RideDetailFragment())
                        .addToBackStack(DriverDetailFragment.class.getName())
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);
            llMain = (LinearLayout) itemView.findViewById(R.id.ll_main);
            binding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
