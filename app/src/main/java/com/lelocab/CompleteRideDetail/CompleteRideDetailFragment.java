package com.lelocab.CompleteRideDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.CompleteRideDetailFragmentBinding;
import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;
import com.lelocab.rides.RidesAdapter;


/**
 * Created by ashish on 14-05-2017.
 */
public class CompleteRideDetailFragment extends Fragment implements ICompleteRideView {

    private BaseActivity mActivity;
    private CompleteRideDetailFragmentBinding binding;
    private GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel;
    private String from;
    private ICompleteRidePresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.complete_ride_detail_fragment, container, false);
        getAllCompleteRideHistoryResponseModel =
                (GetAllCompleteRideHistoryResponseModel) getArguments().getSerializable(GetAllCompleteRideHistoryResponseModel.class.getName());
        mActivity = (BaseActivity) getActivity();
        presenter = new CompleteRidePresenterImpl(mActivity, this);
        ((MainActivity) mActivity).displayHomeButton(false);
        mActivity.setTitle(getString(R.string.ride_details));
        from = getArguments().getString(Constants.FROM);
        if (from != null && from.equals(RidesAdapter.class.getName())) {
            binding.llRating.setVisibility(View.GONE);
        }
        binding.setDto(getAllCompleteRideHistoryResponseModel);
        binding.setCompleteRideDetailFragment(this);
        return binding.getRoot();
    }

    public static CompleteRideDetailFragment newInstance(GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel, Bundle bundle) {
        CompleteRideDetailFragment completeRideDetailFragment = new CompleteRideDetailFragment();
        bundle.putSerializable(GetAllCompleteRideHistoryResponseModel.class.getName(), getAllCompleteRideHistoryResponseModel);
        completeRideDetailFragment.setArguments(bundle);
        return completeRideDetailFragment;
    }

    public void onClickFinish(View view) {
        Intent intent = new Intent(mActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickSubmit(View view) {

        presenter.insertRating(binding.rating.getRating(), getAllCompleteRideHistoryResponseModel);
    }
}
