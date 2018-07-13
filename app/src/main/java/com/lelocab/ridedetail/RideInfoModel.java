package com.lelocab.ridedetail;

import java.io.Serializable;

/**
 * Created by ashish on 06-05-2017.
 */

public class RideInfoModel implements Serializable {
    private String SourceLatitude;
    private String DestinationLongitude;
    private String DestinationAddress;
    private String VehicleName;
    private String DriverPicture;
    private String DriverName;
    private String PerMinCharge;
    private String SourceLongitude;
    private String VehicleType;
    private String BaseFare;
    private String TypeVehicle;
    private String ContactNo;
    private String Address;
    private String VehicleId;
    private String VehicleNo;
    private String RideStatus;
    private String DeviceToken;
    private String DestinationLatitude;
    private String VehicleTypeId;
    private String RideFare;
    private String DriverId;
    private String SourceAddress;

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

    public String getDestinationAddress() {
        return DestinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        DestinationAddress = destinationAddress;
    }

    public String getSourceLongitude() {
        return SourceLongitude;
    }

    public void setSourceLongitude(String sourceLongitude) {
        SourceLongitude = sourceLongitude;
    }

    public String getTypeVehicle() {
        return TypeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        TypeVehicle = typeVehicle;
    }

    public String getRideStatus() {
        return RideStatus;
    }

    public void setRideStatus(String rideStatus) {
        RideStatus = rideStatus;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getDestinationLatitude() {
        return DestinationLatitude;
    }

    public void setDestinationLatitude(String destinationLatitude) {
        DestinationLatitude = destinationLatitude;
    }

    public String getSourceAddress() {
        return SourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        SourceAddress = sourceAddress;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDriverPicture() {
        return DriverPicture;
    }

    public void setDriverPicture(String driverPicture) {
        DriverPicture = driverPicture;
    }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getVehicleTypeId() {
        return VehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = vehicleTypeId;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public String getBaseFare() {
        return BaseFare;
    }

    public void setBaseFare(String baseFare) {
        BaseFare = baseFare;
    }

    public String getRideFare() {
        return RideFare;
    }

    public void setRideFare(String rideFare) {
        RideFare = rideFare;
    }

    public String getPerMinCharge() {
        return PerMinCharge;
    }

    public void setPerMinCharge(String perMinCharge) {
        PerMinCharge = perMinCharge;
    }
}
