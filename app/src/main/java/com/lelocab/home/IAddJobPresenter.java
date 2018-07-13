package com.lelocab.home;


import android.content.res.Resources;
import android.net.Uri;
import android.text.Spanned;


/**
 * Created by admin on 1/7/2015.
 */
public interface IAddJobPresenter {

    Spanned formatPlaceDetails(Resources res, CharSequence name, String id, CharSequence address, CharSequence phoneNumber, Uri websiteUri);


}
