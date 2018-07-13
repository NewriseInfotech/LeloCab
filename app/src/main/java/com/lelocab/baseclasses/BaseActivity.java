package com.lelocab.baseclasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lelocab.R;

/**
 * Created by ashish on 02-05-2017.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showError(EditText editText, String string) {
        editText.setError(string);
        editText.requestFocus();
    }


    private View progressBar;

    /**
     * method call to initialize progress bar dialog
     */
    public void init() {
        progressBar = getLayoutInflater().inflate(
                R.layout.layout_progress, null);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.addView(progressBar);

    }

    /**
     * method call to show progress
     */
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * method call to hide progress
     */
    public void hideProgress() {
        try {
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissProgDialog() {
        try {
            if (mProgDialog != null) {
                if (mProgDialog.isShowing())
                    mProgDialog.dismiss();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showLoadingMessage(String message) {
        try {
            android.view.ContextThemeWrapper themedContext;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                themedContext = new android.view.ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
            } else {
                themedContext = new android.view.ContextThemeWrapper(this, android.R.style.Theme_Light_NoTitleBar);
            }

            if (mProgDialog == null) {
                mProgDialog = new ProgressDialog(themedContext);
                mProgDialog.setCancelable(false);
                mProgDialog.setMessage(message);
                mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgDialog.setProgress(0);
            } else if (mProgDialog.isShowing()) {
                return;
            }
            mProgDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void showProgDialog(String message) {
        try {
            ContextThemeWrapper themedContext;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                themedContext = new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
            } else {
                themedContext = new ContextThemeWrapper(this, android.R.style.Theme_Light_NoTitleBar);
            }

            if (mProgDialog == null) {
                mProgDialog = new ProgressDialog(themedContext);
                mProgDialog.setCancelable(false);
                mProgDialog.setMessage(message);
                mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgDialog.setProgress(0);
            } else if (mProgDialog.isShowing()) {
                return;
            }
            mProgDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
