package com.smile.atozadmin.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smile.atozadmin.LoginMain;
import com.smile.atozadmin.R;

import org.json.JSONObject;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    private static String CHANNEL_ID ="com.smile.atozapp.MyFirebaseMessageing";
    private final int NOTIFICATION_ID = 001;

    @Override
    public void onNewToken(String token) {
        Log.d("fmessage", "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d("From     : ", remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.e("Data", "Data Payload: " + remoteMessage.getData().toString());
            System.out.println("Checking Data Payload");

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e("Data", "Exception: " + e.getMessage());
            }
        }

        if (remoteMessage.getNotification() != null) {
            Log.e("Data", "Notification Body: " + remoteMessage.getNotification().getBody());
            createNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

    }

    void handleDataMessage(JSONObject json){
        Log.e("JSON Data ", json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            //String notificationURL = data.getString("weburl");
            //String imageURL = data.getString("imageurl");
            createNotification(title , message);
        }catch (Exception e){
            Log.d("Error : ",e.getMessage());
        }
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification(String title , String message){
        createNotificationChannel();
        Intent intent = new Intent(this, LoginMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.sparrowiconsmall)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
