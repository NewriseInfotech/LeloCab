package com.lelocab.login;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.Prefs;
import com.lelocab.databinding.LoginActivityBinding;
import com.lelocab.gps.GPSTracker;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 22-04-2017.
 */

public class LoginPresenterImpl implements ILoginPresenter, ApiCallbacks {
    private LoginActivityBinding binding;
    private LoginActivity mActivity;
    private ILoginView iLoginView;
    private LoginRequestModel loginRequestModel;

    public LoginPresenterImpl(LoginActivityBinding binding, LoginActivity mActivity, ILoginView iLoginView) {
        this.binding = binding;
        this.mActivity = mActivity;
        this.iLoginView = iLoginView;
    }


    private boolean isValidateLogin(LoginRequestModel loginRequestModel) {
        if (TextUtils.isEmpty(loginRequestModel.getPhoneNumber())) {
            mActivity.showError(binding.edContactNumber, mActivity.getString(R.string.contact_number_validation));
            return false;
        }
        if (TextUtils.isEmpty(loginRequestModel.getPassword())) {
            mActivity.showError(binding.edPassword, mActivity.getString(R.string.contact_number_validation));
            return false;
        }
        return true;
    }

    @Override
    public void onLoginClick(LoginRequestModel loginRequestModel) {
        loginRequestModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
        loginRequestModel.setRemember(binding.checkRememberMe.isChecked());

        this.loginRequestModel = loginRequestModel;
        if (isValidateLogin(loginRequestModel)) {
            mActivity.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiService.login(loginRequestModel);
            WebServiceCaller.CallWebApi(call, WebApiConstants.LOGIN, mActivity, this);
        }
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        if (anEnum == WebApiConstants.LOGIN) {
            mActivity.hideProgress();
            Prefs.putObjectIntoPref(mActivity, loginRequestModel, LoginRequestModel.class.getName());
            LoginResponseModel loginResponseModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), LoginResponseModel.class);
            Prefs.putObjectIntoPref(mActivity, loginResponseModel, LoginResponseModel.class.getName());
            Prefs.setBoolean(mActivity, Constants.IS_LOGGED_IN, true);
            iLoginView.onLogin(loginResponseModel);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
