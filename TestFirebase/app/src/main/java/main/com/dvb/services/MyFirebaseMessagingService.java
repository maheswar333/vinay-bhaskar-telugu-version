package main.com.dvb.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import main.com.dvb.Constants;
import main.com.dvb.Dashboard_main;
import main.com.dvb.R;

/**
 * Created by artre on 12/28/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.toString());

        try {
            JSONObject json = new JSONObject(remoteMessage.getData().toString());
            handleDataMessage(json);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String type = data.getString("type");
//            //STORE MESSAGES INTO SHAREDPREFERENCES
//            SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_NOTIFICATION_MESSAGE, 0);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("TITLE", title);
//            editor.putString("MESSAGE", message);
//
//            editor.commit();




            Intent intent = new Intent(this, Dashboard_main.class);
            intent.putExtra("TYPE",type);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification noti = new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.dasyam)
                    .setContentIntent(pIntent)
                    .setSound(soundUri)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            noti.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, noti);
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}