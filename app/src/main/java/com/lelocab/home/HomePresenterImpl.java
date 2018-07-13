package com.lelocab.home;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.R;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.databinding.HomeFragmentBinding;
import com.lelocab.login.LoginResponseModel;
import com.lelocab.ridedetail.RideInfoModel;
import com.lelocab.webservice.ApiCallbacks;
import com.lelocab.webservice.ApiClient;
import com.lelocab.webservice.ApiInterface;
import com.lelocab.webservice.WebApiConstants;
import com.lelocab.webservice.WebServiceCaller;

import retrofit2.Call;

/**
 * Created by ashish on 05-05-2017.
 */

public class HomePresenterImpl implements HomeFragmentPresenter, ApiCallbacks {

    private BaseActivity mActivity;
    private HomeFragmentBinding binding;
    private HomeFragmentView view;
    private LoginResponseModel loginResponseModel;
    private CountDownTimer countDownTimer;
    private long time = 1000 * 60;
    private long rideId;
    private boolean isSearchComplete;

    public HomePresenterImpl(BaseActivity mActivity, HomeFragmentBinding binding, HomeFragmentView view) {
        this.mActivity = mActivity;
        this.binding = binding;
        this.view = view;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
    }

    @Override
    public void createRide(CreateRideRequestModel createRideRequestModel) {
        createRideRequestModel.setUserId(loginResponseModel.getId());
        if (isValidate(createRideRequestModel)) {
            mActivity.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiService.createRide(createRideRequestModel);
            WebServiceCaller.CallWebApi(call, WebApiConstants.CREATE_RIDE, mActivity, this);
        }
    }

    @Override
    public void fetchSuitableJob(long rideID) {
        mActivity.showLoadingMessage(mActivity.getString(R.string.driver_search));
        this.rideId = rideID;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", rideId);
        jsonObject.addProperty("UserId", loginResponseModel.getId());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.checkRideStatus(jsonObject);
        WebServiceCaller.CallWebApi(call, WebApiConstants.CHECK_RIDE_STATUS, mActivity, this);
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        if (anEnum == WebApiConstants.CREATE_RIDE) {
            mActivity.hideProgress();
            view.onRideSuccess(WebServiceCaller.getResponseValue(jsonObject));
        }
        if (anEnum == WebApiConstants.CHECK_RIDE_STATUS) {
            if (WebServiceCaller.getResponseValue(jsonObject) == 2) {
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("Id", rideId);
                jsonObject1.addProperty("UserId", loginResponseModel.getId());
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiService.getRideInfo(jsonObject1);
                WebServiceCaller.CallWebApi(call, WebApiConstants.GET_RIDE_INFO, mActivity, this);
            } else {
                if (!isSearchComplete)
                    fetchSuitableJob(rideId);
            }
        }
        if (anEnum == WebApiConstants.GET_RIDE_INFO) {
            mActivity.dismissProgDialog();
            RideInfoModel rideInfoModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), RideInfoModel.class);
            view.onRideAccepted(rideInfoModel);
        }
        if (anEnum == WebApiConstants.GET_RUNNING_RIDE) {
            mActivity.hideProgress();
            RideInfoModel rideInfoModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), RideInfoModel.class);
            view.onGetRunningRide(rideInfoModel);
        }
    }

    @Override
    public void startJobSearching(long rideId) {
        this.rideId = rideId;
        mActivity.showProgress();
        fetchSuitableJob(rideId);
        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("" + millisUntilFinished, " timer");
            }

            public void onFinish() {
                isSearchComplete = true;
                view.onSearchCompleteFaluire();
            }
        }.start();
    }

    @Override
    public void getRunningRide() {
        mActivity.showProgress();
        GetRunningRideRequestModel getRunningRideRequestModel = new GetRunningRideRequestModel();
        getRunningRideRequestModel.setUser(true);
        getRunningRideRequestModel.setUserId(String.valueOf(loginResponseModel.getId()));
        getRunningRideRequestModel.setDriverId("");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.getRunningRide(getRunningRideRequestModel);
        WebServiceCaller.CallWebApi(call, WebApiConstants.GET_RUNNING_RIDE, mActivity, this);
    }


    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum != WebApiConstants.GET_RUNNING_RIDE)
            mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
    }

    private boolean isValidate(CreateRideRequestModel createRideRequestModel) {
        if (TextUtils.isEmpty(createRideRequestModel.getSourceAddress())) {
            mActivity.showError(binding.autoTxtFrom, mActivity.getString(R.string.from_address_validation));
            return false;
        }
        if (TextUtils.isEmpty(createRideRequestModel.getDestinationAddress())) {
            mActivity.showError(binding.autoTxtTo, mActivity.getString(R.string.to_address_validation));
            return false;
        }
        if (createRideRequestModel.getDestinationLatitude() == 0.0) {
            mActivity.showError(binding.autoTxtTo, mActivity.getString(R.string.destination_address_validation));
            return false;
        }
        if (createRideRequestModel.getDestinationLongitude() == 0.0) {
            mActivity.showError(binding.autoTxtTo, mActivity.getString(R.string.destination_address_validation));
            return false;
        }
        if (createRideRequestModel.getVehicleType() == 0 && !createRideRequestModel.isScheduled()) {
            mActivity.showToast(mActivity.getString(R.string.vehicle_validation));
            return false;
        }
        return true;
    }
}
