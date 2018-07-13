package com.lelocab.driverdetail;

import java.io.Serializable;

/**
 * Created by ashish on 12-05-2017.
 */

public class CancelRideRequestModel implements Serializable {
    private String RideId;
    private boolean IsUser;
    private String CancelReason;
    private String UserId;
    private String DriverId;
    private String CancelReasonOther;

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getCancelReasonOther() {
        return CancelReasonOther;
    }

    public void setCancelReasonOther(String cancelReasonOther) {
        CancelReasonOther = cancelReasonOther;
    }

    public String getRideId() {
        return RideId;
    }

    public void setRideId(String rideId) {
        RideId = rideId;
    }

    public boolean isUser() {
        return IsUser;
    }

    public void setUser(boolean user) {
        IsUser = user;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
