package com.example.messychef.utils;

import android.app.Activity;

public class AppFocus {
    private static AppFocus instance;
    private boolean hasFocus;
    private Activity activity;

    private AppFocus() {
    }

    public static AppFocus getInstance() {
        if (instance == null)
            instance = new AppFocus();
        return instance;
    }

    public void setFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public boolean hasFocus() {
        return hasFocus;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
