package com.lelocab.webservice;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lelocab.R;
import com.lelocab.baseclasses.BaseActivity;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 15-09-2016.
 */
public class WebServiceCaller {
    public static void CallWebApi(final Call call, final Enum anEnum, final BaseActivity mActivity, final ApiCallbacks apiCallbacks) {
        call.enqueue(new Callback<JsonObject>() {
                         @Override
                         public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                             if (response.body() != null && isSuccess(response.body())) {
                                 apiCallbacks.onSuccess(response.body(), anEnum);
                             } else if (response.body() == null) {
                                 mActivity.showToast(mActivity.getString(R.string.server_exception));
                                 mActivity.hideProgress();
                             } else {
                                 apiCallbacks.onError(response.body(), anEnum);
                             }
                         }

                         @Override
                         public void onFailure(Call<JsonObject> call, Throwable t) {
                             Log.e("Request failaur", t.toString());
                             mActivity.showToast("Internal Server Error.");
                             mActivity.hideProgress();
                         }
                     }

        );
    }

    /* {
         "ResponseCode":"101", "Status":"Failure", "ResponseMessage":
         "Email Already Exists", "ResponsePacket":null
     }
 */
    private static boolean isSuccess(JsonObject jsonObject) {
        return jsonObject.get("ResponseCode").getAsInt() == 200;
    }

    public static String getResponseMessage(JsonObject jsonObject) {
        return jsonObject.get("ResponseMessage").getAsString();
    }

    public static String getResponseToken(JsonObject jsonObject) {
        return jsonObject.get("token").getAsString();
    }

    public static JsonObject getResponsePacket(JsonObject jsonObject) {
        return jsonObject.getAsJsonObject("ResponsePacket");
    }

    public static long getResponseValue(JsonObject jsonObject) {
        return jsonObject.getAsJsonPrimitive("ResponsePacket").getAsLong();
    }

    public static JsonArray getResponsePacketArray(JsonObject jsonObject) {
        return jsonObject.getAsJsonArray("ResponsePacket");
    }
}
