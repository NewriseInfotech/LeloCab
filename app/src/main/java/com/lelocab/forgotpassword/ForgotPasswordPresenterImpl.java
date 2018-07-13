package com.lelocab.forgotpassword;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.lelocab.R;
import com.lelocab.Utills.Util;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.ForgotPasswordActivityBinding;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 03-05-2017.
 */

class ForgotPasswordPresenterImpl implements IForgotPasswordPresenter, ApiCallbacks {

    private BaseActivity mActivity;
    private IForgotPasswordView iForgotPasswordView;
    private ForgotPasswordActivityBinding binding;

    ForgotPasswordPresenterImpl(ForgotPasswordActivityBinding binding, BaseActivity mActivity, IForgotPasswordView iForgotPasswordView) {
        this.mActivity = mActivity;
        this.binding = binding;
        this.iForgotPasswordView = iForgotPasswordView;
    }

    @Override
    public void password(ForgotPasswordRequestModel forgotPasswordRequestModel) {
        if (isValidate(forgotPasswordRequestModel)) {
            mActivity.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiService.password(forgotPasswordRequestModel);
            WebServiceCaller.CallWebApi(call, WebApiConstants.PASSWORD, mActivity, this);
        }
    }

    private boolean isValidate(ForgotPasswordRequestModel forgotPasswordRequestModel) {
        if (TextUtils.isEmpty(forgotPasswordRequestModel.getEmail())) {
            mActivity.showError(binding.edEmail, mActivity.getString(R.string.email_validation));
            return false;
        }
        if (!Util.getInstance().isValidateEmail(forgotPasswordRequestModel.getEmail())) {
            mActivity.showError(binding.edEmail, mActivity.getString(R.string.correct_email_validation));
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
        mActivity.finish();
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
