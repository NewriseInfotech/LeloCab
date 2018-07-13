package com.lelocab.home.processjob;


import com.lelocab.ridedetail.RideInfoModel;

/**
 * Created by admin on 8/31/2016.
 */
public interface IProcessJobView {

    void onProgressChanged(double percent);

    void onSearchCompleteFaluire(int loop);

    void onAddJobSuccess(long id);

    void onRideAccepted(RideInfoModel rideInfoModel);
}
