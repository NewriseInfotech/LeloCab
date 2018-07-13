package com.lelocab.home;

import java.io.Serializable;

/**
 * Created by ashish on 05-05-2017.
 */

public class CreateRideRequestModel implements Serializable {

    private boolean IsScheduled;
    private String PickUpDateTime;
    private double SourceLatitude;
    private double DestinationLongitude;
    private String DestinationAddress = "";
    private String SourceAddress = "";
    private double DestinationLatitude;
    private double SourceLongitude;
    private long UserId;
    private int VehicleType = 0;

    public String getSourceAddress() {
        return SourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        SourceAddress = sourceAddress;
    }

    public boolean isScheduled() {
        return IsScheduled;
    }

    public void setScheduled(boolean scheduled) {
        IsScheduled = scheduled;
    }

    public String getPickUpDateTime() {
        return PickUpDateTime;
    }

    public void setPickUpDateTime(String pickUpDateTime) {
        PickUpDateTime = pickUpDateTime;
    }

    public double getSourceLatitude() {
        return SourceLatitude;
    }

    public void setSourceLatitude(double sourceLatitude) {
        SourceLatitude = sourceLatitude;
    }

    public double getDestinationLongitude() {
        return DestinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        DestinationLongitude = destinationLongitude;
    }

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        DestinationAddress = destinationAddress;
    }

    public double getDestinationLatitude() {
        return DestinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        DestinationLatitude = destinationLatitude;
    }

    public double getSourceLongitude() {
        return SourceLongitude;
    }

    public void setSourceLongitude(double sourceLongitude) {
        SourceLongitude = sourceLongitude;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public int getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(int vehicleType) {
        VehicleType = vehicleType;
    }
}
