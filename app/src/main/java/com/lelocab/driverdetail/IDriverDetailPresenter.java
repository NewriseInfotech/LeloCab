package com.lelocab.driverdetail;

import com.lelocab.ridedetail.RideInfoModel;

/**
 * Created by ashish on 11-05-2017.
 */

public interface IDriverDetailPresenter {
    void cancelRide(RideInfoModel rideInfoModel, long rideId);
}
