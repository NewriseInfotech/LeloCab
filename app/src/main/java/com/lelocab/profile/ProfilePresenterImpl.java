package com.lelocab.profile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.MainActivity;
import com.lelocab.Utills.Prefs;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 25-05-2017.
 */

public class ProfilePresenterImpl implements IProfilePresenter, ApiCallbacks {
    private MainActivity mActivity;
    private IProfileView view;
    private ApiInterface apiService;
    private LoginResponseModel loginResponseModel;

    public ProfilePresenterImpl(MainActivity mActivity, IProfileView view) {
        this.mActivity = mActivity;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getWalletDetail() {
        mActivity.showProgress();
        GetWalletDetailRequestModel getWalletDetailRequestModel = new GetWalletDetailRequestModel();
        getWalletDetailRequestModel.setDriverId("0");
        getWalletDetailRequestModel.setUser(true);
        getWalletDetailRequestModel.setUserId(String.valueOf(loginResponseModel.getId()));
        Call<JsonObject> call = apiService.getWalletDetail(getWalletDetailRequestModel);
        WebServiceCaller.CallWebApi(call, WebApiConstants.GET_WALLET_DETAIL, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.GET_WALLET_DETAIL) {
            GetWalletDetailResponseModel getWalletDetailResponseModel = new Gson()
                    .fromJson(WebServiceCaller.getResponsePacket(jsonObject), GetWalletDetailResponseModel.class);
            view.onGetWalletDetail(getWalletDetailResponseModel);
        }

    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
