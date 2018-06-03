package com.pablosanchezegido.petcity.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.pablosanchezegido.petcity.R;

import java.util.Locale;

public class NotificationBuilder {

    public static final String REMINDERS_CHANNEL_ID = "Recordatorios";
    public static final String USER_FULL_NAME = "userFullName";

    public static void createReminderLocalNotification(Context context, String channelId, String userFullName) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create notification channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(REMINDERS_CHANNEL_ID, REMINDERS_CHANNEL_ID, importance, notificationManager);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(R.string.noti_title))
                .setContentText(context.getString(R.string.noti_detail, userFullName))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.noti_detail, userFullName)))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        notificationManager.notify(generateUniqueId(), builder.build());
    }

    private static void createNotificationChannel(String channelId, String name, int importance, NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /* Generate unique id based on current day, hour, minute and second
       It could get repeated on same date and time from each month
       but it's not common to keep a notification so long */
    private static int generateUniqueId() {
        return CalendarUtilsKt.getDateInteger("ddHHmmss");
    }
}
