package com.example.messychef.utils;

public class AppFocus {
    private static AppFocus instance;
    private boolean hasFocus;

    private AppFocus() {}

    public static AppFocus getInstance() {
        if(instance == null)
            instance = new AppFocus();
        return instance;
    }

    public void setFocus(boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public boolean hasFocus() {
        return hasFocus;
    }
}
