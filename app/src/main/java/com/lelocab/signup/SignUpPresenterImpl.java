package com.lelocab.signup;

import android.text.TextUtils;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.R;
import com.lelocab.Utills.Prefs;
import com.lelocab.Utills.Util;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.SignUpActivityBinding;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 22-04-2017.
 */

public class SignUpPresenterImpl implements ISignUpPresenter, ApiCallbacks {
    private SignUpActivityBinding binding;
    private BaseActivity mActivity;
    private ISignUpView iSignUpView;

    public SignUpPresenterImpl(SignUpActivityBinding binding, BaseActivity mActivity, ISignUpView iSignUpView) {
        this.binding = binding;
        this.mActivity = mActivity;
        this.iSignUpView = iSignUpView;
    }

    @Override
    public void onSignUpClick(SignUpRequestModel signUpRequestModel) {
        signUpRequestModel.setDeviceToken(FirebaseInstanceId.getInstance().getToken());
        if (isValidateSignUp(signUpRequestModel)) {
            mActivity.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiService.registerUser(signUpRequestModel);
            WebServiceCaller.CallWebApi(call, WebApiConstants.SIGN_UP, mActivity, this);
        }
    }

    private boolean isValidateSignUp(SignUpRequestModel signUpRequestModel) {
        if (TextUtils.isEmpty(signUpRequestModel.getName())) {
            mActivity.showError(binding.edName, mActivity.getString(R.string.name_validation));
            return false;
        }
        if (TextUtils.isEmpty(signUpRequestModel.getPhoneNumber())) {
            mActivity.showError(binding.edContactNumber, mActivity.getString(R.string.contact_number_validation));
            return false;
        }
        if (TextUtils.getTrimmedLength(signUpRequestModel.getPhoneNumber()) < 10) {
            mActivity.showError(binding.edContactNumber, mActivity.getString(R.string.correct_contact_number_validation));
            return false;
        }
        if (TextUtils.isEmpty(signUpRequestModel.getEmail())) {
            mActivity.showError(binding.edEmail, mActivity.getString(R.string.email_validation));
            return false;
        }
        if (!Util.getInstance().isValidateEmail(signUpRequestModel.getEmail())) {
            mActivity.showError(binding.edEmail, mActivity.getString(R.string.correct_email_validation));
            return false;
        }
        if (TextUtils.isEmpty(signUpRequestModel.getPassword())) {
            mActivity.showError(binding.edPassword, mActivity.getString(R.string.password_validation));
            return false;
        }
        if (TextUtils.isEmpty(signUpRequestModel.getConfirmPassword())) {
            mActivity.showError(binding.edConfirmPassword, mActivity.getString(R.string.confirm_password_validation));
            return false;
        }
        if (!signUpRequestModel.getConfirmPassword().equals(signUpRequestModel.getPassword())) {
            mActivity.showError(binding.edConfirmPassword, mActivity.getString(R.string.password_not_match_validation));
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        if (anEnum == WebApiConstants.SIGN_UP) {
            mActivity.hideProgress();
            LoginResponseModel loginResponseModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), LoginResponseModel.class);
            iSignUpView.onSignUp(loginResponseModel);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
