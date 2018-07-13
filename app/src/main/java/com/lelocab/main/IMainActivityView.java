package com.lelocab.main;

import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;

/**
 * Created by ashish on 13-05-2017.
 */

public interface IMainActivityView {
    void onLogout();


    void onCompleteRideDetail(GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel);
}
