package com.lelocab.rides;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by ashish on 14-05-2017.
 */

public class RidesPresenterImpl implements IRidesPresenter, ApiCallbacks {
    private BaseActivity mActivity;
    private IRidesView view;
    private LoginResponseModel loginResponseModel;

    public RidesPresenterImpl(BaseActivity mActivity, IRidesView view) {
        this.mActivity = mActivity;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
    }

    @Override
    public void getAllCompleteRideHistory() {
        GetAllCompletedRideHistory getAllCompletedRideHistory =
                new GetAllCompletedRideHistory(true, loginResponseModel.getId(), 0);

        mActivity.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getAllCompleteRideHistory(getAllCompletedRideHistory);
        WebServiceCaller.CallWebApi(call, WebApiConstants.GET_ALL_COMPLETE_RIDE_HISTORY, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.GET_ALL_COMPLETE_RIDE_HISTORY) {
            ArrayList<GetAllCompleteRideHistoryResponseModel> getAllCompleteRideHistoryResponseModels = new Gson().fromJson(WebServiceCaller.getResponsePacketArray(jsonObject).toString(), new TypeToken<List<GetAllCompleteRideHistoryResponseModel>>() {
            }.getType());
            view.onGetAllCompleteRideHistory(getAllCompleteRideHistoryResponseModels);
        }

    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
