package com.example.todoList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlarmReceiver extends BroadcastReceiver {
    private int i1;

    @Override
    public void onReceive(Context context, Intent intent) {
        i1 = intent.getIntExtra("notification_id", i1);
        String name = intent.getStringExtra("name");

        NotificationHelper notificationHelper = new NotificationHelper(context, name);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(i1, nb.build());
        //Intent intent1 = (context,notificationHelper);
    }

}
