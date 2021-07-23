package com.example.messychef.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

public class ActivityStarter {
    final private Activity owner;
    private Intent cacheIntent;

    public ActivityStarter(Activity owner) {
        this.owner = owner;
    }

    public ActivityStarter setActivity(Class<?> cls) {
        cacheIntent = new Intent(owner, cls);
        return this;
    }

    public ActivityStarter setExtra(String id, Serializable object) {
        cacheIntent.putExtra(id, object);
        return this;
    }


    public void start() {
        Intent tmp = cacheIntent;
        cacheIntent = null;
        owner.startActivity(tmp);
    }

    public void start(Class<?> cls) {
        Intent intent = new Intent(owner, cls);
        owner.startActivity(intent);
    }

    public void start(String name) {
        String fullName = "com.example.messychef." + name;
        Intent intent = new Intent(fullName);
        owner.startActivity(intent);
    }


}
