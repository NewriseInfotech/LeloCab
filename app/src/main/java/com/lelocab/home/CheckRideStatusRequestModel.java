package com.lelocab.home;

import java.io.Serializable;

/**
 * Created by ashish on 05-05-2017.
 */

public class CheckRideStatusRequestModel implements Serializable {
    private long Id;

    private long UserId;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
