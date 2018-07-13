package com.lelocab.webservice;

import com.google.gson.JsonObject;
import com.lelocab.CompleteRideDetail.RatingRequestModel;
import com.lelocab.cancelride.CancelRideRequestModel;
import com.lelocab.forgotpassword.ForgotPasswordRequestModel;
import com.lelocab.home.CreateRideRequestModel;
import com.lelocab.home.GetRunningRideRequestModel;
import com.lelocab.login.LoginRequestModel;
import com.lelocab.profile.GetWalletDetailRequestModel;
import com.lelocab.rides.GetAllCompletedRideHistory;
import com.lelocab.signup.SignUpRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 15-09-2016.
 */
public interface ApiInterface {

    @POST("UserAccount/LoginUser")
    Call<JsonObject> login(@Body LoginRequestModel loginRequestModel);

    @POST("UserAccount/RegisterUser")
    Call<JsonObject> registerUser(@Body SignUpRequestModel signUpRequestModel);

    @POST("UserAccount/ForgotPassword")
    Call<JsonObject> password(@Body ForgotPasswordRequestModel forgotPasswordRequestModel);

    @POST("Ride/CreateRide")
    Call<JsonObject> createRide(@Body CreateRideRequestModel createRideRequestModel);

    @POST("Ride/CheckRideStatus")
    Call<JsonObject> checkRideStatus(@Body JsonObject createRideRequestModel);

    @POST("Ride/GetRideDriverInfo")
    Call<JsonObject> getRideInfo(@Body JsonObject createRideRequestModel);

    @POST("UserAccount/Logout")
    Call<JsonObject> logout(@Body JsonObject UserId);

    @POST("Ride/CancelRide")
    Call<JsonObject> CancelRide(@Body CancelRideRequestModel cancelRideRequestModel);

    @POST("Ride/GetAllCompleteRideHistory")
    Call<JsonObject> getAllCompleteRideHistory(@Body GetAllCompletedRideHistory getAllCompletedRideHistory);

    @POST("Ride/GetCompleteRideDetail")
    Call<JsonObject> getCompleteRideDetail(@Body JsonObject jsonObject);

    @POST("UserAccount/VerifyOTP")
    Call<JsonObject> VerifyUser(@Body JsonObject jsonObject);

    @POST("UserAccount/ReSendOTP")
    Call<JsonObject> reSendOTP(@Body JsonObject jsonObject);

    @POST("Rating/InsertRating")
    Call<JsonObject> insertRating(@Body RatingRequestModel ratingRequestModel);

    @POST("Wallet/GetWalletDetail")
    Call<JsonObject> getWalletDetail(@Body GetWalletDetailRequestModel getWalletDetailRequestModel);

    @POST("Ride/GetRunningRide")
    Call<JsonObject> getRunningRide(@Body GetRunningRideRequestModel getRunningRideRequestModel);
}
