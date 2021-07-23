package com.example.messychef.timer_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.messychef.ExecuteRecipeActivity;
import com.example.messychef.ShowTimerStepFragment;

public class TimerNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TimerServiceCache cache = TimerServiceCache.getInstance();
        TimerService timerService = cache.getTimerService();
        timerService.runSnooze();
    }
}