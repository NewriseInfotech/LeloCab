package com.lelocab.profile;

import java.util.ArrayList;

/**
 * Created by ashish on 25-05-2017.
 */

public class GetWalletDetailResponseModel {
    private String Amount;

    private ArrayList<WalletHistory> WalletHistory;

    private String Id;

    private String UserId;

    private String DriverId;

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public ArrayList<com.lelocab.profile.WalletHistory> getWalletHistory() {
        return WalletHistory;
    }

    public void setWalletHistory(ArrayList<com.lelocab.profile.WalletHistory> walletHistory) {
        WalletHistory = walletHistory;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
