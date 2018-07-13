package com.lelocab.cancelride;

import android.content.Intent;

import com.google.gson.JsonObject;
import com.lelocab.MainActivity;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 24-05-2017.
 */

public class CancelRidePresenterImpl implements ICancelRidePresenter, ApiCallbacks {
    private BaseActivity mActivity;
    private ICancelRideView view;
    private ApiInterface apiService;

    public CancelRidePresenterImpl(BaseActivity mActivity, ICancelRideView view) {
        this.mActivity = mActivity;
        this.view = view;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void CancelRide(CancelRideRequestModel cancelRideRequestModel) {
        mActivity.showProgress();
        Call<JsonObject> call = apiService.CancelRide(cancelRideRequestModel);
        WebServiceCaller.CallWebApi(call, WebApiConstants.CANCEL_RIDE, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.CANCEL_RIDE) {
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
        mActivity.finish();
    }
}
