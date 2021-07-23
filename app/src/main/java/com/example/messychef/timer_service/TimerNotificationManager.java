package com.example.messychef.timer_service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import com.example.messychef.R;

class TimerNotificationManager {

    private static final String CHANNEL_ID = "timer-notification-id";

    private final Context owner;
    private NotificationManager manager;


    TimerNotificationManager(Context context) {
        this.owner = context;
        createNotificationChannel();
    }


    private void createNotificationChannel() {
        CharSequence name = owner.getString(R.string.channel_name);
        String description = owner.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        manager = owner.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }


}
