package com.lelocab.driverdetail;

import com.google.gson.JsonObject;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.ridedetail.RideInfoModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 11-05-2017.
 */

public class DriverDetailPresenterImpl implements IDriverDetailPresenter, ApiCallbacks {
    private BaseActivity mActivity;
    private IDriverDetailView view;
    private LoginResponseModel loginResponseModel;

    public DriverDetailPresenterImpl(BaseActivity mActivity, IDriverDetailView view) {
        this.mActivity = mActivity;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
    }

    @Override
    public void cancelRide(RideInfoModel rideInfoModel, long rideId) {
        /*CancelRideRequestModel cancelRideRequestModel = new CancelRideRequestModel();
        cancelRideRequestModel.setRideId(String.valueOf(rideId));
        cancelRideRequestModel.setUserId(String.valueOf(loginResponseModel.getId()));
        cancelRideRequestModel.setCancelReason("0");
        cancelRideRequestModel.setUser(false);
        cancelRideRequestModel.setDriverId(rideInfoModel.getDriverId());
        cancelRideRequestModel.setCancelReasonOther("");

        mActivity.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.CancelRide(cancelRideRequestModel);
        WebServiceCaller.CallWebApi(call, WebApiConstants.CANCEL_RIDE, mActivity, this);*/
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.CANCEL_RIDE) {
            mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
            mActivity.onBackPressed();
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
