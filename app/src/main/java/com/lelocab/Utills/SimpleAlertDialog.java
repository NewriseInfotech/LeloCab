package com.lelocab.Utills;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;


/**
 * Created by atul on 19/01/15.
 */
public class SimpleAlertDialog {

    private CallbackAlertDialog callbackPositiveBtn;
    private CallbackAlertDialog callbackNegativeBtn;
    public String txtPositiveBtn = "Ok";
    public String txtNegativeBtn = "Cancel";
    protected AlertDialog dialog;


    public SimpleAlertDialog(Context context, String title, String message, CallbackAlertDialog callbackPositiveBtn, CallbackAlertDialog callbackNegativeBtn) {
        this(context, false, title, message, null, null, callbackPositiveBtn, callbackNegativeBtn);
    }


    public SimpleAlertDialog(Context context, boolean isCancellable, String title, String message, String txtPositiveBtn, String txtNegativeBtn, final CallbackAlertDialog callbackPositiveBtn, final CallbackAlertDialog callbackNegativeBtn) {
        this.callbackPositiveBtn = callbackPositiveBtn;
        this.callbackNegativeBtn = callbackNegativeBtn;

        if (!TextUtils.isEmpty(txtPositiveBtn))
            this.txtPositiveBtn = txtPositiveBtn;
        if (!TextUtils.isEmpty(txtNegativeBtn))
            this.txtNegativeBtn = txtNegativeBtn;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);

        builder.setMessage("" + message);

        if (this.callbackPositiveBtn != null) {
            builder.setPositiveButton(this.txtPositiveBtn, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
        }

        if (this.callbackNegativeBtn != null) {
            builder.setNegativeButton(this.txtNegativeBtn, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
        }

        dialog = builder.create();
        dialog.setCancelable(isCancellable);
    }

    public void show() {
        try {
            dialog.show();
            dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callbackPositiveBtn != null) {
                        callbackPositiveBtn.onClickAlertBtn(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });

            dialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callbackNegativeBtn != null) {
                        callbackNegativeBtn.onClickAlertBtn(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
        } catch (Exception e) {

        }
    }

}
