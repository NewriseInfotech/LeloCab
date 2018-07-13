package com.lelocab.verification;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 20-05-2017.
 */

public class VerificationPresenterImpl implements IVerificationPresenter, ApiCallbacks {
    private IVerificationView view;
    private BaseActivity mActivity;
    private ApiInterface apiService;
    private boolean validate;

    public VerificationPresenterImpl(IVerificationView view, BaseActivity mActivity) {
        this.view = view;
        this.mActivity = mActivity;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void verifyUser(String otp, LoginResponseModel loginResponseModel) {
        if (isValidate(otp, loginResponseModel)) {
            mActivity.showProgress();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserId", loginResponseModel.getId());
            jsonObject.addProperty("OTP", otp);
            jsonObject.addProperty("PhoneNumber", loginResponseModel.getPhoneNumber());
            Call<JsonObject> call = apiService.VerifyUser(jsonObject);
            WebServiceCaller.CallWebApi(call, WebApiConstants.VERIFY_OTP, mActivity, this);
        }
    }

    @Override
    public void reSendOTP(String otp, LoginResponseModel loginResponseModel) {
        mActivity.showProgress();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", loginResponseModel.getId());
        Call<JsonObject> call = apiService.reSendOTP(jsonObject);
        WebServiceCaller.CallWebApi(call, WebApiConstants.RE_SEND_OTP, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.VERIFY_OTP) {
            LoginResponseModel loginResponseModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), LoginResponseModel.class);
            Prefs.setBoolean(mActivity, Constants.IS_LOGGED_IN, true);
            Prefs.putObjectIntoPref(mActivity, loginResponseModel, LoginResponseModel.class.getName());
            view.onVerifyUser(loginResponseModel);
        }
        if (anEnum == WebApiConstants.RE_SEND_OTP) {
            LoginResponseModel loginResponseModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), LoginResponseModel.class);
            view.onResendOtp(loginResponseModel);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }

    public boolean isValidate(String otp, LoginResponseModel loginResponseModel) {
        if (TextUtils.isEmpty(otp)) {
            mActivity.showToast("Please enter Otp to continue");
            return false;
        }
        if (!loginResponseModel.getOTP().equals(otp)) {
            mActivity.showToast("Please enter a valid Otp to continue");
            return false;
        }

        return true;
    }
}
