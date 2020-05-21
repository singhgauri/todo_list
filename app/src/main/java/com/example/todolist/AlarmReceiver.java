package com.example.todolist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    int i1;
    String name;
    @Override
    public void onReceive(Context context, Intent intent) {
        i1 = intent.getIntExtra("notiid", i1);
        name = intent.getStringExtra("name");

        NotificationHelper notificationHelper = new NotificationHelper(context,name);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(i1, nb.build());
        //Intent intent1 = (context,notificationHelper);
    }

}
