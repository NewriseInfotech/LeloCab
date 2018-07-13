package com.lelocab.Utills;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by admin on 02-12-2016.
 */

public class Util {
    private static Util util = null;

    private Util() {

    }

    public static Util getInstance() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    public void datePick(Activity activity, DatePickerDialog.OnDateSetListener onDateSetListener, long millisecods, boolean isMax, boolean isMin) {
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(activity, onDateSetListener, year, month, day);
        Field mDatePickerField;
        try {
            mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
            mDatePickerField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isMax)
            dialog.getDatePicker().setMaxDate(millisecods);
        if (isMin)
            dialog.getDatePicker().setMinDate(millisecods);
        dialog.show();
    }

    /**
     * method call to hide keyboard
     *
     * @param activity
     */
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        } catch (Exception exception) {

        }
    }


    /**
     * Method use to check whether user is online or not.
     *
     * @param context context of current activity.
     * @return true if user is online else returns false.
     */
    public final boolean isOnline(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        return false;
    }

    public boolean isValidateEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
