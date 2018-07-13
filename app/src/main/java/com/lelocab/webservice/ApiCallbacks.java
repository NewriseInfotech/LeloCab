package com.lelocab.webservice;

import com.google.gson.JsonObject;

/**
 * Created by ashish on 26-10-2016.
 */

public interface ApiCallbacks {
    void onSuccess(JsonObject jsonObject, Enum anEnum);

    void onError(JsonObject jsonObject, Enum anEnum);
}
