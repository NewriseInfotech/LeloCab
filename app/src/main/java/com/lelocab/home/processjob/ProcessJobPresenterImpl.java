package com.lelocab.home.processjob;

import android.content.Intent;
import android.os.CountDownTimer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lelocab.MainActivity;
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
 * Created by ashish on 05-05-2017.
 */
public class ProcessJobPresenterImpl implements IProcessJobPresenter, ApiCallbacks {
    private BaseActivity mActivity;
    private boolean isTimerComplete;
    private int loopCount = 1;
    private int reamaining = 100;
    private IProcessJobView processView;
    private CountDownTimer countDownTimer;
    private int getJobById = 123;
    private int completeJob = 321;
    private int driverImage = 12;
    private final int paymentRequest = 789;
    private long rideId;
    private long time = 1000 * 60 * 3;
    private boolean isFetchJob;
    private LoginResponseModel loginResponseModel;
    private boolean isCountDownTimer = false;

    public ProcessJobPresenterImpl(BaseActivity mActivity) {
        this.mActivity = mActivity;
        loginResponseModel = Prefs.getObjectFromPref(mActivity, LoginResponseModel.class.getName());
    }

    @Override
    public void startJobSearching(long rideId) {
        this.rideId = rideId;
        isTimerComplete = true;
        fetchSuitableJob();
        countDownTimer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                reamaining = (int) ((millisUntilFinished * 100) / time);
                processView.onProgressChanged(100 - ((millisUntilFinished * 100) / time));
            }

            public void onFinish() {
                processView.onProgressChanged(100);
                isTimerComplete = false;
                loopCount++;
                isFetchJob = false;
                processView.onSearchCompleteFaluire(loopCount);
            }
        }.start();
    }

    @Override
    public void cancelJobSearching() {

    }

    @Override
    public void fetchSuitableJob() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", rideId);
        jsonObject.addProperty("UserId", loginResponseModel.getId());
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiService.checkRideStatus(jsonObject);
        WebServiceCaller.CallWebApi(call, WebApiConstants.CHECK_RIDE_STATUS, mActivity, this);
    }

    @Override
    public void setProcessView(IProcessJobView processView) {
        this.processView = processView;
    }

    @Override
    public void onSuccess(JsonObject jsonObject, Enum anEnum) {
        if (anEnum == WebApiConstants.CHECK_RIDE_STATUS) {
            if (WebServiceCaller.getResponseValue(jsonObject) == 2) {
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("Id", rideId);
                jsonObject1.addProperty("UserId", loginResponseModel.getId());
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiService.getRideInfo(jsonObject1);
                WebServiceCaller.CallWebApi(call, WebApiConstants.GET_RIDE_INFO, mActivity, this);
                countDownTimer.cancel();
                isCountDownTimer = true;
            } else {
                if (!isCountDownTimer)
                    fetchSuitableJob();
            }
        } else if (anEnum == WebApiConstants.GET_RIDE_INFO) {
            RideInfoModel rideInfoModel = new Gson().fromJson(WebServiceCaller.getResponsePacket(jsonObject), RideInfoModel.class);
            processView.onRideAccepted(rideInfoModel);
        }
    }

    @Override
    public void onError(JsonObject jsonObject, Enum anEnum) {
        mActivity.hideProgress();
        if (anEnum == WebApiConstants.CHECK_RIDE_STATUS) {
            mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
            Intent intent = new Intent(mActivity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mActivity.startActivity(intent);
        } else if (anEnum == WebApiConstants.GET_RIDE_INFO) {
            mActivity.showToast(WebServiceCaller.getResponseMessage(jsonObject));
        }
    }
}
