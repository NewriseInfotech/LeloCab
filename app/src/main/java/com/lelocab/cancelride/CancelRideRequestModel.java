package com.lelocab.cancelride;

import java.io.Serializable;

/**
 * Created by ashish on 24-05-2017.
 */

public class CancelRideRequestModel implements Serializable {
    private long RideId;
    private boolean IsUser;
    private int CancelReason;
    private long UserId;
    private String DriverId;
    private String CancelReasonOther;

    public long getRideId() {
        return RideId;
    }

    public void setRideId(long rideId) {
        RideId = rideId;
    }

    public boolean isUser() {
        return IsUser;
    }

    public void setUser(boolean user) {
        IsUser = user;
    }

    public int getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(int cancelReason) {
        CancelReason = cancelReason;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

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
}
