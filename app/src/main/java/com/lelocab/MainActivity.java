package com.lelocab;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lelocab.CompleteRideDetail.CompleteRideDetailFragment;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.Prefs;
import com.lelocab.baseclasses.BaseActivity;
import com.lelocab.home.HomeFragment;
import com.lelocab.login.LoginActivity;
import com.lelocab.login.LoginRequestModel;
import com.lelocab.main.IMainActivityPresenter;
import com.lelocab.main.IMainActivityView;
import com.lelocab.main.MainActivityPresenterImpl;
import com.lelocab.notification.NotificationResponseModel;
import com.lelocab.profile.ProfileFragment;
import com.lelocab.rating.RatingFragment;
import com.lelocab.ridedetail.RideDetailFragment;
import com.lelocab.ridedetail.RideInfoModel;
import com.lelocab.rides.GetAllCompleteRideHistoryResponseModel;
import com.lelocab.rides.RidesFragment;
import com.lelocab.yourrides.YourRidesFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, IMainActivityView {
    private Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce;

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private IMainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mNotificationReciever, new IntentFilter(Constants.NOTIFICATION));

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        turnGPSOn();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        presenter = new MainActivityPresenterImpl(this, this);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    onBackPressed();
                } else {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
        setTitle(getString(R.string.app_name));
        startHomeFrag();

        notificationClickListner();
    }

    private void notificationClickListner() {
        NotificationResponseModel notificationResponseModel = (NotificationResponseModel) getIntent().getSerializableExtra(NotificationResponseModel.class.getName());

        if (notificationResponseModel != null && notificationResponseModel.getNotificationType() == 5) {
            Prefs.putObjectIntoPref(this, null, RideInfoModel.class.getName());
            presenter.GetCompleteRideDetail(notificationResponseModel);
        }
        if (notificationResponseModel != null && notificationResponseModel.getNotificationType() == 6) {
            if (getSupportFragmentManager().findFragmentById(R.id.flContent).getClass().getName().equals(HomeFragment.class.getName())) {
                HomeFragment frg = null;
                frg = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        }
    }

    public void startHomeFrag() {
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }

    public void displayHomeButton(boolean flag) {
        //display home
        getSupportActionBar().setDisplayShowHomeEnabled(flag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
        getSupportActionBar().setHomeButtonEnabled(flag);
        if (flag) {
            toggle.syncState();
        } else {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.app_name)
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.Logout();

                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                break;
            case R.id.nav_home:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_your_rides:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, new RidesFragment())
                        .commit();
                break;
            case R.id.nav_lelo_money:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flContent, new ProfileFragment())
                        .commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    BroadcastReceiver mNotificationReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationResponseModel notificationResponseModel = (NotificationResponseModel) intent.getSerializableExtra(NotificationResponseModel.class.getName());

            if (notificationResponseModel.getNotificationType() == 5) {

                Prefs.putObjectIntoPref(context, null, RideInfoModel.class.getName());
                presenter.GetCompleteRideDetail(notificationResponseModel);
            }
        }
    };

    /**
     * Method to turn on GPS
     **/

    public void turnGPSOn() {
        try {

            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } catch (Exception e) {

        }
    }

    // Method to turn off the GPS
    public void turnGPSOff() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps")) { //if gps is enabled

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    // turning off the GPS if its in on state. to avoid the battery drain.

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationReciever);
        turnGPSOff();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onLogout() {
        LoginRequestModel loginRequestModel = Prefs.getObjectFromPref(getApplicationContext(), LoginRequestModel.class.getName());
        if (loginRequestModel != null && !loginRequestModel.isRemember()) {
            Prefs.putObjectIntoPref(getApplicationContext(), null, LoginRequestModel.class.getName());
        }
        Prefs.setBoolean(this, Constants.IS_LOGGED_IN, false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onCompleteRideDetail(GetAllCompleteRideHistoryResponseModel getAllCompleteRideHistoryResponseModel) {
        Bundle bundle = new Bundle();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContent, CompleteRideDetailFragment.newInstance(getAllCompleteRideHistoryResponseModel, bundle))
                .commit();
    }


}
