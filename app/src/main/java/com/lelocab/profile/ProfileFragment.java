package com.lelocab.profile;

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
import com.lelocab.Utills.Prefs;
import com.lelocab.databinding.ProfileFragmentDataBinding;
import com.lelocab.login.LoginResponseModel;

/**
 * Created by ashish on 25-05-2017.
 */

public class ProfileFragment extends Fragment implements IProfileView {

    ProfileFragmentDataBinding binding;
    private MainActivity mActivity;
    private IProfilePresenter presenter;
    private LoginResponseModel loginResponseModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        mActivity = (MainActivity) getActivity();
        presenter = new ProfilePresenterImpl(mActivity, this);
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
        mActivity.setTitle(getString(R.string.profile));
        binding.setLoginResponseModel(loginResponseModel);
        binding.setProfileFragment(this);
        presenter.getWalletDetail();
        return binding.getRoot();
    }

    @Override
    public void onGetWalletDetail(GetWalletDetailResponseModel getWalletDetailResponseModel) {
        binding.setDto(getWalletDetailResponseModel);
    }

    public void onClickShare(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Sample share text");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
    }
}
