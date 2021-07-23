package com.example.messychef.timer_service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.messychef.ExecuteRecipeActivity;
import com.example.messychef.R;

class TimerNotificationManager {

    private static final String CHANNEL_ID = "timer-notification-id";
    private static final int STEP_NOTIFY_ID = 0;
    private static final int GLOBAL_NOTIFY_ID = 1;
    private static final int NEXT_STEP_ID = 2;


    private final TimerService owner;
    private NotificationManager manager;
    private NotificationCompat.Builder stepNotificationBuilder;
    private NotificationCompat.Builder globalNotificationBuilder;

    TimerNotificationManager(TimerService context) {
        this.owner = context;
        createNotificationChannel();
        createNotificationBuilders();
    }


    private void createNotificationChannel() {
        CharSequence name = owner.getString(R.string.channel_name);
        String description = owner.getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        manager = owner.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    private void createNotificationBuilders() {
        stepNotificationBuilder = setupStepNotificationBuilder();
        globalNotificationBuilder = setupGlobalNotificationBuilder();
    }

    private NotificationCompat.Builder setupStepNotificationBuilder() {
        Intent intent = new Intent(owner, TimerNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(owner, 0, intent, 0);

        return new NotificationCompat.Builder(owner, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(owner.getString(R.string.step_timer_over))
                .setContentText("Arrivederci")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(pendingIntent)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder setupGlobalNotificationBuilder() {
        Intent snoozeIntent = new Intent(owner, TimerNotificationReceiver.class);
        snoozeIntent.setAction("");
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(owner, 0, snoozeIntent, 0);

        return new NotificationCompat.Builder(owner, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(owner.getString(R.string.global_timer_over))
                .setContentText("Arrivederci")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(snoozePendingIntent)
                .setAutoCancel(true);
    }

    void showStepNotification() {
        Notification notification = stepNotificationBuilder.build();
        manager.notify(STEP_NOTIFY_ID, notification);

    }

    void showGlobalNotification() {

        Notification notification = globalNotificationBuilder.build();
        manager.notify(GLOBAL_NOTIFY_ID, notification);

    }

    void showNextStepNotification() {
        Intent intent = new Intent(owner, ExecuteRecipeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(ExecuteRecipeActivity.AUTO_NEXT);
        PendingIntent pendingIntent = PendingIntent.getActivity(owner, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new NotificationCompat.Builder(owner, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(owner.getString(R.string.global_timer_over))
                .setContentText("Arrivederci")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
        manager.notify(NEXT_STEP_ID, notification);
    }


}
