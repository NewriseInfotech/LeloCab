package com.lelocab.rides;

import java.io.Serializable;

/**
 * Created by ashish on 14-05-2017.
 */

public class GetAllCompleteRideHistoryResponseModel implements Serializable {
    private RideDetail RideDetail;
    private UserRideDetail UserRideDetail;
    private DriverVehicleDetail DriverVehicleDetail;

    public RideDetail getRideDetail() {
        return RideDetail;
    }

    public void setRideDetail(RideDetail rideDetail) {
        this.RideDetail = rideDetail;
    }

    public UserRideDetail getUserRideDetail() {
        return UserRideDetail;
    }

    public void setUserRideDetail(UserRideDetail userRideDetail) {
        this.UserRideDetail = userRideDetail;
    }

    public DriverVehicleDetail getDriverVehicleDetail() {
        return DriverVehicleDetail;
    }

    public void setDriverVehicleDetail(DriverVehicleDetail driverVehicleDetail) {
        this.DriverVehicleDetail = driverVehicleDetail;
    }
}
