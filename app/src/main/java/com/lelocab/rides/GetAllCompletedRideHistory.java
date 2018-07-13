package com.lelocab.rides;

import java.io.Serializable;

/**
 * Created by ashish on 14-05-2017.
 */

public class GetAllCompletedRideHistory implements Serializable {
    private boolean IsUser;

    private long UserId;

    private long DriverId;

    public GetAllCompletedRideHistory(boolean isUser, long userId, long driverId) {
        IsUser = isUser;
        UserId = userId;
        DriverId = driverId;
    }

    public boolean isUser() {

        return IsUser;
    }

    public void setUser(boolean user) {
        IsUser = user;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public long getDriverId() {
        return DriverId;
    }

    public void setDriverId(long driverId) {
        DriverId = driverId;
    }
}
