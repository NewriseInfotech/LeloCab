package com.lelocab.rides;

import java.io.Serializable;

/**
 * Created by ashish on 14-05-2017.
 */

public class DriverVehicleDetail implements Serializable {
    private String VehicleName;
    private String DriverPicture;
    private String DriverName;
    private String PerMinCharge;
    private String VehicleType;
    private String BaseFare;
    private String TypeVehicle;
    private String ContactNo;
    private String Address;
    private String VehicleId;
    private String VehicleNo;
    private String DeviceToken;
    private String VehicleTypeId;
    private String RideFare;
    private String DriverId;

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getDriverPicture() {
        return DriverPicture;
    }

    public void setDriverPicture(String driverPicture) {
        DriverPicture = driverPicture;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getPerMinCharge() {
        return PerMinCharge;
    }

    public void setPerMinCharge(String perMinCharge) {
        PerMinCharge = perMinCharge;
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

    public String getTypeVehicle() {
        return TypeVehicle;
    }

    public void setTypeVehicle(String typeVehicle) {
        TypeVehicle = typeVehicle;
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

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getVehicleNo() {
        return VehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        VehicleNo = vehicleNo;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getVehicleTypeId() {
        return VehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        VehicleTypeId = vehicleTypeId;
    }

    public String getRideFare() {
        return RideFare;
    }

    public void setRideFare(String rideFare) {
        RideFare = rideFare;
    }

    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }
}
