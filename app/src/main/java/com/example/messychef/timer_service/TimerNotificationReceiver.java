package com.example.messychef.timer_service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.messychef.ExecuteRecipeActivity;
import com.example.messychef.ShowTimerStepFragment;
import com.example.messychef.utils.AppFocus;

import java.util.List;

public class TimerNotificationReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onReceive(Context context, Intent intent) {
        TimerServiceCache cache = TimerServiceCache.getInstance();
        TimerService timerService = cache.getTimerService();
        timerService.runSnooze();
    }

}