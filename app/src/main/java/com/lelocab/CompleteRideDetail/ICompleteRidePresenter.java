package com.lelocab.CompleteRideDetail;

import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;

/**
 * Created by ashish on 23-05-2017.
 */

public interface ICompleteRidePresenter {
    void insertRating(float rating, GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel);

}
