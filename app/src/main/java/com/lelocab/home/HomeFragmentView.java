package com.lelocab.home;

import com.lelocab.ridedetail.RideInfoModel;

/**
 * Created by ashish on 05-05-2017.
 */

public interface HomeFragmentView {

    void onRideSuccess(long responseValue);

    void onSearchCompleteFaluire();

    void onRideAccepted(RideInfoModel rideInfoModel);

    void onGetRunningRide(RideInfoModel rideInfoModel);
}
