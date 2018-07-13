package com.lelocab.rides;

import java.io.Serializable;

/**
 * Created by ashish on 14-05-2017.
 */

public class UserRideDetail implements Serializable {
    private String Name;
    private String SourceLatitude;
    private String DestinationLongitude;
    private String RideId;
    private String Email;
    private String DestinationAddress;
    private String PhoneNumber;
    private String DestinationLatitude;
    private String SourceLongitude;
    private String UserId;
    private String SourceAddress;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSourceLatitude() {
        return SourceLatitude;
    }

    public void setSourceLatitude(String sourceLatitude) {
        SourceLatitude = sourceLatitude;
    }

    public String getDestinationLongitude() {
        return DestinationLongitude;
    }

    public void setDestinationLongitude(String destinationLongitude) {
        DestinationLongitude = destinationLongitude;
    }

    public String getRideId() {
        return RideId;
    }

    public void setRideId(String rideId) {
        RideId = rideId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        DestinationAddress = destinationAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getDestinationLatitude() {
        return DestinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        DestinationLatitude = destinationLatitude;
    }

    public String getSourceLongitude() {
        return SourceLongitude;
    }

    public void setSourceLongitude(String sourceLongitude) {
        SourceLongitude = sourceLongitude;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getSourceAddress() {
        return SourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        SourceAddress = sourceAddress;
    }
}
