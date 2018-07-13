package com.lelocab.rides;

import java.io.Serializable;

/**
 * Created by ashish on 14-05-2017.
 */

public class RideDetail implements Serializable {
    private String Fare;
    private String WalletPayment;
    private String Distance;
    private String EndAt;
    private String StartAt;
    private String VehicleType;
    private String UserId;
    private String TotalTime;
    private String WaitingAmount;
    private String CashPayment;
    private String RideId;
    private String RunningAmount;
    private String Rating;
    private String CreatedOn;
    private String VehicleId;
    private String Discount;
    private String TotalAmount;
    private String ExtraAmount;
    private String RideFare;
    private String DriverId;
    private String ServiceTax;
    private String DiscountType;

    public String getWalletPayment() {
        return WalletPayment;
    }

    public void setWalletPayment(String walletPayment) {
        WalletPayment = walletPayment;
    }

    public String getCashPayment() {
        return CashPayment;
    }

    public void setCashPayment(String cashPayment) {
        CashPayment = cashPayment;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getRideFare() {
        return RideFare;
    }

    public void setRideFare(String rideFare) {
        RideFare = rideFare;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public String getWaitingAmount() {
        return WaitingAmount;
    }

    public void setWaitingAmount(String waitingAmount) {
        WaitingAmount = waitingAmount;
    }

    public String getServiceTax() {
        return ServiceTax;
    }

    public void setServiceTax(String serviceTax) {
        ServiceTax = serviceTax;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getEndAt() {
        return EndAt;
    }

    public void setEndAt(String endAt) {
        EndAt = endAt;
    }

    public String getStartAt() {
        return StartAt;
    }

    public void setStartAt(String startAt) {
        StartAt = startAt;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getRideId() {
        return RideId;
    }

    public void setRideId(String rideId) {
        RideId = rideId;
    }

    public String getRunningAmount() {
        return RunningAmount;
    }

    public void setRunningAmount(String runningAmount) {
        RunningAmount = runningAmount;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getExtraAmount() {
        return ExtraAmount;
    }

    public void setExtraAmount(String extraAmount) {
        ExtraAmount = extraAmount;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }
}
