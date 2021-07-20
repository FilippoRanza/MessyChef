package com.example.messychef.utils.view_find_adapter;

import android.app.Activity;
import android.view.View;

public class FindViewAdapterFactory {

    private FindViewAdapterFactory() {}

    public static AbstractFindViewAdapter<?> fromView(View v) {
        return new ViewFindViewAdapter<>(v);
    }

    public static AbstractFindViewAdapter<?> fromActivity(Activity activity) {
        return new ActivityFindViewAdapter<>(activity);
    }

}
