package com.lelocab.home;

/**
 * Created by ashish on 05-05-2017.
 */

public interface HomeFragmentPresenter {
    void createRide(CreateRideRequestModel createRideRequestModel);

    void fetchSuitableJob(long rideID);

    void startJobSearching(long rideId);

    void getRunningRide();
}
