package com.lelocab.notification;

import java.io.Serializable;

/**
 * Created by ashish on 08-05-2017.
 */

public class NotificationResponseModel implements Serializable {
    public int RideId;
    public int UserId;
    public int NotificationType;
    public String Message;

    @Override
    public String toString() {
        return "NotificationResponseModel{" +
                "RideId=" + RideId +
                ", UserId=" + UserId +
                ", NotificationType=" + NotificationType +
                ", Message='" + Message + '\'' +
                '}';
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(int notificationType) {
        NotificationType = notificationType;
    }

    public int getRideId() {
        return RideId;
    }

    public void setRideId(int rideId) {
        RideId = rideId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
