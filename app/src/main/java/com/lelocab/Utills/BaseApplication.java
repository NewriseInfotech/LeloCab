package com.lelocab.Utills;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.holidaycheck.permissify.DialogText;
import com.holidaycheck.permissify.PermissifyConfig;
import com.lelocab.R;

import java.util.HashMap;

/**
 * Created by admin on 9/30/2016.
 */

public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PermissifyConfig permissifyConfig = new PermissifyConfig.Builder()
                .withDefaultTextForPermissions(new HashMap<String, DialogText>() {{
                    put(Manifest.permission_group.LOCATION, new DialogText(R.string.location_rationale, R.string.location_deny_dialog));
                }})
                .build();

        PermissifyConfig.initDefault(permissifyConfig);

        /*// UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP*/
    }

}
