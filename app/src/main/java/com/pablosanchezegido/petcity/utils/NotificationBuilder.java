package com.pablosanchezegido.petcity.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.pablosanchezegido.petcity.R;

public class NotificationBuilder {

    public static final String REMINDERS_CHANNEL_ID = "Recordatorios";

    public static void createReminderLocalNotification(Context context, String channelId) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(REMINDERS_CHANNEL_ID, REMINDERS_CHANNEL_ID, importance, notificationManager);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(R.string.noti_title))
                .setContentText(context.getString(R.string.noti_detail))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }

    private static void createNotificationChannel(String channelId, String name, int importance, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
