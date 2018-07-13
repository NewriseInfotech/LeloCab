package com.lelocab.main;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.Utills.Prefs;
import com.lelocab.notification.NotificationResponseModel;
import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 13-05-2017.
 */

public class MainActivityPresenterImpl implements IMainActivityPresenter, ApiCallbacks {

    private BaseActivity mActivity;
    private IMainActivityView view;
    private LoginResponseModel loginResponseModel;

    public MainActivityPresenterImpl(BaseActivity mActivity, IMainActivityView view) {
        this.mActivity = mActivity;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
    }

    @Override
    public void Logout() {
        mActivity.showProgress();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserId", loginResponseModel.getId());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.logout(jsonObject);
        WebServiceCaller.CallWebApi(call, WebApiConstants.LOGOUT, mActivity, this);
    }

    @Override
    public void GetCompleteRideDetail(NotificationResponseModel notificationResponseModel) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", notificationResponseModel.getRideId());
        jsonObject.addProperty("UserId", loginResponseModel.getId());
        mActivity.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getCompleteRideDetail(jsonObject);
        WebServiceCaller.CallWebApi(call, WebApiConstants.GET_COMPLETE_RIDE_DETAIL, mActivity, this);
    }


    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.LOGOUT) {
            view.onLogout();
        }
        if (anEnum == WebApiConstants.GET_COMPLETE_RIDE_DETAIL) {
            GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel =
                    new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), GetAllCompleteRideHistoryResponseModel.class);
            view.onCompleteRideDetail(getAllCompleteRideHistoryResponseModel);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
