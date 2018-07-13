package com.lelocab.splash;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.Prefs;
import com.lelocab.databinding.SplashActivityBinding;
import com.lelocab.login.LoginActivity;
import com.lelocab.login.LoginResponseModel;

/**
 * Created by ashish on 21-04-2017.
 */

public class SplashActivity extends AppCompatActivity {
    SplashActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (Prefs.getBoolean(getApplicationContext(), Constants.IS_LOGGED_IN)) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


}
