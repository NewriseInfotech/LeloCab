package com.lelocab.profile;

import java.io.Serializable;

/**
 * Created by ashish on 25-05-2017.
 */

public class GetWalletDetailRequestModel implements Serializable {
    private boolean IsUser;

    private String UserId;

    private String DriverId;

    public boolean isUser() {
        return IsUser;
    }

    public void setUser(boolean user) {
        IsUser = user;
    }

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
}
