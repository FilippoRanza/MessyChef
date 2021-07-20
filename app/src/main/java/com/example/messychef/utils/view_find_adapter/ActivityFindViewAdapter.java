package com.example.messychef.utils.view_find_adapter;

import android.app.Activity;
import android.view.View;

class ActivityFindViewAdapter<T extends View> extends AbstractFindViewAdapter<T> {

    private final Activity activity;

    ActivityFindViewAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public T findViewById(int id) {
        return activity.findViewById(id);
    }
}
