package com.pablosanchezegido.petcity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pablosanchezegido.petcity.utils.NotificationBuilder;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationBuilder.createReminderLocalNotification(context, NotificationBuilder.REMINDERS_CHANNEL_ID);
    }
}
