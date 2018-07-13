package com.lelocab.forgotpassword;

import java.io.Serializable;

/**
 * Created by ashish on 03-05-2017.
 */

public class ForgotPasswordRequestModel implements Serializable {
    private String Email;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
