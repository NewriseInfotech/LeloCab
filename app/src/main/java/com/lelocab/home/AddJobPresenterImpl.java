package com.lelocab.home;

import android.content.res.Resources;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;

import com.lelocab.MainActivity;
import com.lelocab.R;

/**
 * Created by ashish on 22-04-2017.
 */
public class AddJobPresenterImpl implements IAddJobPresenter {
    public AddJobPresenterImpl(MainActivity mActivity) {
    }

    @Override
    public Spanned formatPlaceDetails(Resources res, CharSequence name, String id, CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber, websiteUri));
    }
}
