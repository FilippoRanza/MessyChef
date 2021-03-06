package com.example.messychef.timer_service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.messychef.ExecuteRecipeActivity;
import com.example.messychef.R;
import com.example.messychef.utils.AppFocus;

class TimerNotificationManager {

    private static final String CHANNEL_ID = "timer-notification-id";
    private static final int STEP_NOTIFY_ID = 0;
    private static final int GLOBAL_NOTIFY_ID = 1;
    private static final int NEXT_STEP_ID = 2;


    private final TimerService owner;
    private NotificationManager manager;
    private NotificationCompat.Builder stepNotificationBuilder;

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
    }

    private NotificationCompat.Builder setupStepNotificationBuilder() {
        Intent intent = new Intent(owner, TimerNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(owner, 0, intent, 0);

        return new NotificationCompat.Builder(owner, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(owner.getString(R.string.step_timer_over))
                .setContentText(owner.getString(R.string.snooze))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setDeleteIntent(pendingIntent)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder setupGlobalNotificationBuilder() {
        Activity activity = AppFocus.getInstance().getActivity();
        Intent snoozeIntent = new Intent(activity, TimerNotificationReceiver.class);
        snoozeIntent.setAction("");
        PendingIntent snoozePendingIntent = PendingIntent
                .getBroadcast(activity, 0, snoozeIntent, 0);

        Intent restartIntent = new Intent(activity, ExecuteRecipeActivity.class);
        restartIntent.setAction(ExecuteRecipeActivity.AUTO_NEXT);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(activity);
        taskStackBuilder.addNextIntentWithParentStack(restartIntent);
        PendingIntent restartPendingIntent = taskStackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(owner.getString(R.string.global_timer_over))
                .setContentText(owner.getString(R.string.snooze))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDeleteIntent(snoozePendingIntent)
                .setAutoCancel(true);

        if (!AppFocus.getInstance().hasFocus()) {
            builder.setContentIntent(restartPendingIntent);
        } else {
            builder.setContentIntent(snoozePendingIntent);
        }

        return builder;
    }

    void showStepNotification() {
        Notification notification = stepNotificationBuilder.build();
        manager.notify(STEP_NOTIFY_ID, notification);

    }

    void showGlobalNotification() {
        NotificationCompat.Builder builder = setupGlobalNotificationBuilder();
        Notification notification = builder.build();
        manager.notify(GLOBAL_NOTIFY_ID, notification);

    }

}
