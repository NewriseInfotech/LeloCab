package com.lelocab.verification;

import com.lelocab.login.LoginResponseModel;

/**
 * Created by ashish on 20-05-2017.
 */

public interface IVerificationPresenter {
    void verifyUser(String otp, LoginResponseModel loginResponseModel);

    void reSendOTP(String otp, LoginResponseModel loginResponseModel);
}
