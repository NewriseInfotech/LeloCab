package com.lelocab.notification;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.lelocab.MainActivity;
import com.lelocab.R;
import com.lelocab.Utills.Constants;
import com.lelocab.baseclasses.BaseActivity;

import java.util.List;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public final static String MY_ACTION = "MY_ACTION";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + ((ArrayMap<String, String>) remoteMessage.getData()).get("message").toString());
        sendNotification(remoteMessage.getData().values().toArray()[1].toString());
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param message
     */
    private void sendNotification(String message) {
        NotificationResponseModel notificationResponseModel = new Gson().fromJson(message, NotificationResponseModel.class);
        Log.d(TAG, "From: " + notificationResponseModel.toString());

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(getApplicationContext().getPackageName().toString())) {
            isActivityFound = true;
        }

        if (isActivityFound) {
            Intent intent = new Intent(Constants.NOTIFICATION);
            Bundle bundle = new Bundle();
            bundle.putSerializable(NotificationResponseModel.class.getName(), notificationResponseModel);
            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        } else {
            Vibrator vibrator;
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);

            Intent intent = new Intent();
            intent.setAction(MY_ACTION);
            intent.putExtra("DATAPASSED", 1);
            sendBroadcast(intent);

            Random r = new Random();
            int randomNumber = (r.nextInt(80) + 65);
            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent1.putExtra("from", "notification");
            intent1.putExtra(NotificationResponseModel.class.getName(), notificationResponseModel);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(notificationResponseModel.getMessage())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(randomNumber, notificationBuilder.build());
        }
    }

    /**
     * public enum NotificationType
     * {
     * [Display(Name = "Request")]
     * RideRequest = 1,
     * [Display(Name = "Accept")]
     * Accept = 2,
     * [Display(Name = "Decline")]
     * Decline = 3,
     * [Display(Name = "Cancel")]
     * Cancel = 4,
     * [Display(Name = "Complete")]
     * Complete = 5,
     * [Display(Name = "Running")]
     * Running = 6,
     * [Display(Name = "Scheduled")]
     * Scheduled = 7
     * }
     */
}