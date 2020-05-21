package com.example.todolist;


import android.annotation.TargetApi;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
        import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
        import android.support.v4.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    String name;

    public NotificationHelper(Context base,String name) {
        super(base);
        this.name=name;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("ToDo List Reminder!")
                .setContentIntent(pendingIntent)
                .setContentText("Task : "+ name)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icicon);

    }
}

