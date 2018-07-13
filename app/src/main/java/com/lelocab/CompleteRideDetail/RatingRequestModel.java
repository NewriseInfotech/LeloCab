package com.lelocab.CompleteRideDetail;

import java.io.Serializable;

/**
 * Created by ashish on 23-05-2017.
 */

public class RatingRequestModel implements Serializable {
    private String RideId;
    private float Rating;
    private String RatingBy;
    private String RatingTo;
    private String ID;

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getRideId() {
        return RideId;
    }

    public void setRideId(String rideId) {
        RideId = rideId;
    }

    public String getRatingBy() {
        return RatingBy;
    }

    public void setRatingBy(String ratingBy) {
        RatingBy = ratingBy;
    }

    public String getRatingTo() {
        return RatingTo;
    }

    public void setRatingTo(String ratingTo) {
        RatingTo = ratingTo;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
