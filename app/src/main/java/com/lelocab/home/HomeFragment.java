package com.lelocab.home;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.CallbackAlertDialog;
import com.lelocab.Utills.Constants;
import com.lelocab.Utills.PolylineDrawer;
import com.lelocab.Utills.Prefs;
import com.lelocab.Utills.SimpleAlertDialog;
import com.lelocab.Utills.Util;
import com.lelocab.cancelride.CancelRideActivity;
import com.lelocab.databinding.HomeFragmentBinding;
import com.lelocab.gps.GPSTracker;
import com.lelocab.ridedetail.RideInfoModel;

import java.util.Calendar;


/**
 * Created by ashish on 22-04-2017.
 */

public class HomeFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, HomeFragmentView, RadioGroup.OnCheckedChangeListener {
    private HomeFragmentBinding binding;
    private MapView mapView;
    private GoogleMap googleMap;
    private AutoCompleteTextView autoTxtFrom;
    private AutoCompleteTextView autoTxtTo;
    private TextView mPlaceDetailsAttribution;
    private TextView mPlaceDetailsText;
    private PlaceAutocompleteAdapter mAdapter;
    private LatLng latLngFrom;
    private LatLng latLngTo;
    private Marker markerFrom;
    private Marker markerTo;
    private static final String LOCATION_TYPE_FROM = "location_from";
    private static final String LOCATION_TYPE_TO = "location_to";
    public GoogleApiClient mGoogleApiClient;
    private PolylineDrawer polylineDrawer;
    private MainActivity mActivity;
    public static final String TAG = HomeFragment.class.getSimpleName();
    private IAddJobPresenter iLocationPresenter;
    private GPSTracker gps;
    double latitude;
    double longitude;
    private HomeFragmentPresenter presenter;
    private CreateRideRequestModel createRideRequestModel;
    private long rideId;
    private RideInfoModel rideInfoModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        mActivity = (MainActivity) getActivity();
        presenter = new HomePresenterImpl(mActivity, binding, this);
        createRideRequestModel = new CreateRideRequestModel();
        createRideRequestModel.setVehicleType(1);
        binding.setIsAddressSelected(false);
        binding.setHomeFragment(this);
        binding.llCarSelector.rgCars.setOnCheckedChangeListener(this);
        mActivity.setTitle("LeloCab");
        mActivity.displayHomeButton(true);
        initialize(binding.getRoot());
        initMap(savedInstanceState, binding.getRoot());
        initPlacesAutoComplete(binding.getRoot());


        return binding.getRoot();
    }

    private void initialize(View view) {
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                    .enableAutoManage(mActivity, 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();

        iLocationPresenter = new AddJobPresenterImpl(mActivity);
    }

    public void onBookNowClick(View view) {
        createRideRequestModel.setDestinationAddress(autoTxtTo.getText().toString().trim());
        createRideRequestModel.setSourceAddress(autoTxtFrom.getText().toString().trim());
        createRideRequestModel.setScheduled(false);

        presenter.createRide(createRideRequestModel);
    }

    public void onClickCancel(View view) {

        Intent intent = new Intent(mActivity, CancelRideActivity.class);
        intent.putExtra(RideInfoModel.class.getName(), rideInfoModel);
        intent.putExtra("RideId", rideId);
        startActivity(intent);

    }

    private int mYear, mMonth, mDay, mHour, mMinute;

    public void onOutStationClick(View view) {
        final Dialog dialog;
        dialog = new Dialog(mActivity, R.style.DialogSlideAnimZoom);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_time_select_dialog);
        //Get yesterday's date
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        final int year, month, day;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        final TextView tvDate = (TextView) dialog.findViewById(R.id.tv_date);
        final TextView tvTime = (TextView) dialog.findViewById(R.id.tv_time);
        Button btnBookNow = (Button) dialog.findViewById(R.id.btnBookNow);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                tvTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRideRequestModel.setPickUpDateTime(tvDate.getText().toString().trim() + " " + tvTime.getText().toString().trim());
                createRideRequestModel.setDestinationAddress(autoTxtTo.getText().toString().trim());
                createRideRequestModel.setSourceAddress(autoTxtFrom.getText().toString().trim());
                createRideRequestModel.setScheduled(true);
                presenter.createRide(createRideRequestModel);
                dialog.cancel();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    /**
     *
     */
    public void initPlacesAutoComplete(View view) {
        autoTxtFrom = (AutoCompleteTextView) view.findViewById(R.id.autoTxtFrom);
        autoTxtTo = (AutoCompleteTextView) view.findViewById(R.id.autoTxtTo);
        mPlaceDetailsText = (TextView) view.findViewById(R.id.mPlaceDetailsText);
        mPlaceDetailsAttribution = (TextView) view.findViewById(R.id.mPlaceDetailsAttribution);

        autoTxtFrom.setOnItemClickListener(new MyAutoCompleteClickListener(LOCATION_TYPE_FROM));
        autoTxtTo.setOnItemClickListener(new MyAutoCompleteClickListener(LOCATION_TYPE_TO));

        LatLngBounds AUSTRALIA = new LatLngBounds(
                new LatLng(-44, 113), new LatLng(-10, 154));
        mAdapter = new PlaceAutocompleteAdapter(mActivity, mGoogleApiClient, null, null);
        autoTxtFrom.setAdapter(mAdapter);
        autoTxtTo.setAdapter(mAdapter);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("GoogleApiClient", "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(mActivity,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRideSuccess(long RideID) {
        this.rideId = RideID;
        autoTxtFrom.setEnabled(false);
        autoTxtTo.setEnabled(false);
        presenter.fetchSuitableJob(RideID);
    }

    @Override
    public void onSearchCompleteFaluire() {
        new SimpleAlertDialog(mActivity, false, getString(R.string.app_name), "No Driver found. do you want to search again", "Retry", "Cancel", new CallbackAlertDialog() {
            @Override
            public void onClickAlertBtn(AlertDialog dialog) {
                presenter.startJobSearching(rideId);
                dialog.dismiss();
            }
        }, new CallbackAlertDialog() {
            @Override
            public void onClickAlertBtn(AlertDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onRideAccepted(RideInfoModel rideInfoModel) {
        this.rideInfoModel = rideInfoModel;
        binding.llCarSelector.llHomeBottom.setVisibility(View.GONE);
        binding.llRideDetail.llDriverDetailBottom.setVisibility(View.VISIBLE);
        binding.llRideDetail.setRideInfoModel(rideInfoModel);
    }

    @Override
    public void onGetRunningRide(final RideInfoModel rideInfoModel) {
        this.rideInfoModel = rideInfoModel;
        binding.llCarSelector.llHomeBottom.setVisibility(View.GONE);
        binding.llRideDetail.llDriverDetailBottom.setVisibility(View.VISIBLE);
        binding.llRideDetail.setRideInfoModel(rideInfoModel);
        binding.llRideDetail.btnCancel.setVisibility(Integer.parseInt(rideInfoModel.getRideStatus()) == 6 || Integer.parseInt(rideInfoModel.getRideStatus()) == 2 ? View.GONE : View.VISIBLE);
        autoTxtFrom.setEnabled(false);
        autoTxtTo.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.autoTxtFrom.setText(rideInfoModel.getSourceAddress());
                binding.autoTxtTo.setText(rideInfoModel.getDestinationAddress());
            }
        }, 3000);
        googleMap.clear();
        latLngTo = new LatLng(Double.parseDouble(rideInfoModel.getDestinationLatitude())
                , Double.parseDouble(rideInfoModel.getDestinationLongitude()));
        latLngFrom = new LatLng(Double.parseDouble(rideInfoModel.getSourceLatitude())
                , Double.parseDouble(rideInfoModel.getSourceLongitude()));

        markerFrom = googleMap.addMarker(new MarkerOptions()
                .position(latLngFrom)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        markerTo = googleMap.addMarker(new MarkerOptions()
                .position(latLngTo)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        if (latLngFrom != null && latLngTo != null) drawPathOnMap(latLngFrom, latLngTo);


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_outstation:
                createRideRequestModel.setVehicleType(4);
                break;
            case R.id.rb_mini:
                createRideRequestModel.setVehicleType(1);
                break;
            case R.id.rb_seduen:
                createRideRequestModel.setVehicleType(2);
                break;
            case R.id.rb_suv:
                createRideRequestModel.setVehicleType(3);
                break;
        }
    }


    private class MyAutoCompleteClickListener implements AdapterView.OnItemClickListener {
        private String locationType;

        private MyAutoCompleteClickListener(String locationType) {
            this.locationType = locationType;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            if (locationType.equals(LOCATION_TYPE_FROM)) {
                placeResult.setResultCallback(mUpdtPlaceDetCallbackFrom);
            } else if (locationType.equals(LOCATION_TYPE_TO)) {
                placeResult.setResultCallback(mUpdtPlaceDetCallbackTo);
            }
        }
    }


    /**
     * For getting 'From'(Source) lat lng and draw marker on map
     */
    private ResultCallback<PlaceBuffer> mUpdtPlaceDetCallbackFrom
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }

            final Place place = places.get(0);
            autoTxtFrom.clearFocus();
            latLngFrom = place.getLatLng();
            createRideRequestModel.setSourceLatitude(latLngFrom.latitude);
            createRideRequestModel.setSourceLongitude(latLngFrom.longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLngFrom, 13);
            googleMap.animateCamera(yourLocation);
            if (markerFrom != null) markerFrom.remove();
            markerFrom = googleMap.addMarker(new MarkerOptions()
                    .position(latLngFrom)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            if (latLngFrom != null && latLngTo != null) drawPathOnMap(latLngFrom, latLngTo);

            mPlaceDetailsText.setText(iLocationPresenter.formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }
            places.release();
        }
    };


    /**
     * For getting 'To'(Destination) lat lng and draw marker on map
     */
    private ResultCallback<PlaceBuffer> mUpdtPlaceDetCallbackTo
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            Util.getInstance().hideSoftKeyboard(mActivity);
            final Place place = places.get(0);
            autoTxtTo.clearFocus();
            latLngTo = place.getLatLng();
            createRideRequestModel.setDestinationLatitude(latLngTo.latitude);
            createRideRequestModel.setDestinationLongitude(latLngTo.longitude);
            if (markerTo != null) markerTo.remove();
            markerTo = googleMap.addMarker(new MarkerOptions()
                    .position(latLngTo)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            if (latLngFrom != null && latLngTo != null) drawPathOnMap(latLngFrom, latLngTo);

            mPlaceDetailsText.setText(iLocationPresenter.formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }
            places.release();
        }
    };


    /**
     * @param savedInstanceState
     */
    private void initMap(Bundle savedInstanceState, View view) {
        try {
            MapsInitializer.initialize(mActivity);
        } catch (Exception e) {
            Log.e("Address Map", "Could not initialize google play", e);
        }

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        //Gets to GoogleMap from the MapView and does initialization stuff

        if (mapView != null) {
            mapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;
        gps = new GPSTracker(mActivity);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            String address = gps.getLocationAddress(latitude, longitude);
            if (TextUtils.isEmpty(address)) {
                mActivity.showToast("enable to get address from your location.");
            }
            binding.autoTxtFrom.setText(address);
            createRideRequestModel.setSourceLatitude(latitude);
            createRideRequestModel.setSourceLongitude(longitude);
            createRideRequestModel.setSourceAddress(autoTxtFrom.getText().toString().trim());
            // \n is for new line
            latLngFrom = new LatLng(latitude, longitude);
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        if (latLngFrom != null) {
            markerFrom = googleMap.addMarker(new MarkerOptions()
                    .position(latLngFrom)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLngFrom, 13);
            this.googleMap.animateCamera(yourLocation);
        } else gps.showSettingsAlert();
        if (polylineDrawer == null)
            polylineDrawer = new PolylineDrawer(googleMap, Color.RED, 5);

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
            return;
        } else {
            this.googleMap.setMyLocationEnabled(true);

            if (latLngFrom != null && latLngTo != null)
                drawPathOnMap(latLngFrom, latLngTo);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (googleMap != null && latLngFrom != null && latLngTo != null) {
                    googleMap.clear();
                    if (markerTo != null) markerTo.remove();

                    if (latLngTo != null)
                        markerTo = googleMap.addMarker(new MarkerOptions()
                                .position(latLngTo)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    if (markerFrom != null) markerFrom.remove();

                    if (latLngFrom != null)
                        markerFrom = googleMap.addMarker(new MarkerOptions()
                                .position(latLngFrom)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    if (latLngFrom != null && latLngTo != null) drawPathOnMap(latLngFrom, latLngTo);
                }
            }
        }, 2000);
    }

    /**
     * @param latLngFrom
     * @param latLngTo
     */
    private void drawPathOnMap(final LatLng latLngFrom, final LatLng latLngTo) {
        if (!Util.getInstance().isOnline(mActivity)) {
            Toast.makeText(mActivity, "Internet not available", Toast.LENGTH_LONG).show();
            return;
        }

        if (googleMap == null || polylineDrawer == null)
            return;

        if (polylineDrawer.polyline != null) {
            polylineDrawer.polyline.remove();
        }
        polylineDrawer.drawPathOnMap(latLngFrom, latLngTo);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(latLngFrom);
        builder.include(latLngTo);
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
        if (googleMap != null)
            googleMap.animateCamera(cu);
        Location locationA = new Location("point A");

        locationA.setLatitude(latLngFrom.latitude);
        locationA.setLongitude(latLngFrom.longitude);

        Location locationB = new Location("point B");

        locationB.setLatitude(latLngTo.latitude);
        locationB.setLongitude(latLngTo.longitude);

        float distance = locationA.distanceTo(locationB);
        binding.setIsAddressSelected(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int i = 0, len = permissions.length; i < len; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    onMapReady(googleMap);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (mGoogleApiClient == null)
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                    .enableAutoManage(mActivity, 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();

        presenter.getRunningRide();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();

        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
