package com.lelocab.verification;

import com.lelocab.login.LoginResponseModel;

/**
 * Created by ashish on 20-05-2017.
 */

public interface IVerificationView {
    void onVerifyUser(LoginResponseModel loginResponseModel);

    void onResendOtp(LoginResponseModel loginResponseModel);
}
