package com.lelocab.CompleteRideDetail;

import android.content.Intent;

import com.google.gson.JsonObject;
import com.lelocab.MainActivity;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 23-05-2017.
 */

public class CompleteRidePresenterImpl implements ICompleteRidePresenter, ApiCallbacks {

    private BaseActivity mActivity;
    private ICompleteRideView view;
    private LoginResponseModel loginResponseModel;
    private ApiInterface apiService;

    public CompleteRidePresenterImpl(BaseActivity mActivity, ICompleteRideView view) {
        this.mActivity = mActivity;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
        apiService = ApiClient.getClient().create(ApiInterface.class);

    }

    @Override
    public void insertRating(float rating, GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel) {
        mActivity.showProgress();

        RatingRequestModel ratingRequestModel = new RatingRequestModel();
        ratingRequestModel.setID("0");
        ratingRequestModel.setRating(rating);
        ratingRequestModel.setRatingBy(String.valueOf(loginResponseModel.getId()));
        ratingRequestModel.setRatingTo(getAllCompleteRideHistoryResponseModel.getDriverVehicleDetail().getDriverId());
        ratingRequestModel.setRideId(getAllCompleteRideHistoryResponseModel.getRideDetail().getRideId());

        Call<JsonObject> call = apiService.insertRating(ratingRequestModel);
        WebServiceCaller.CallWebApi(call, WebApiConstants.INSERT_RATING, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.INSERT_RATING) {
            mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mActivity.startActivity(intent);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }
}
