package com.pablosanchezegido.petcity.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.pablosanchezegido.petcity.receivers.AlarmBroadcastReceiver;

public class AlarmHelper {

    private static final int NUM_DAYS_BEFORE = 1;

    private Context context;
    private AlarmManager alarmManager;

    public AlarmHelper(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void scheduleAlarmAt(long timestamp) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, intent, 0);

        alarmManager.set(AlarmManager.RTC, getTargetTimestamp(timestamp), pi);
    }

    private long getTargetTimestamp(long timestamp) {
        return timestamp - (NUM_DAYS_BEFORE * 24 * 3600 * 1000);
    }
}
