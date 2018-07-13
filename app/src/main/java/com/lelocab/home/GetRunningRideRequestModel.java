package com.lelocab.home;

/**
 * Created by ashish on 01-06-2017.
 */

public class GetRunningRideRequestModel {

    private String UserId;
    private String DriverId;
    private boolean IsUser;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public boolean isUser() {
        return IsUser;
    }

    public void setUser(boolean user) {
        IsUser = user;
    }
}
