package com.example.messychef.timer_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class TimerNotificationReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {
        TimerServiceCache cache = TimerServiceCache.getInstance();
        TimerService timerService = cache.getTimerService();
        timerService.runSnooze();
    }

}