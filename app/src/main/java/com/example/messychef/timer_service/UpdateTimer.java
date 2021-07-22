package com.example.messychef.timer_service;

import android.os.Binder;

public abstract class UpdateTimer extends Binder {
    abstract public void updateTimer(int global, int step);

    abstract public void done();
}
