package com.example.messychef.utils;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.messychef.utils.view_find_adapter.AbstractFindViewAdapter;
import com.example.messychef.utils.view_find_adapter.FindViewAdapterFactory;

public class FieldInitializer {

    private final AbstractFindViewAdapter<?> owner;


    public FieldInitializer(Activity owner) {
        this.owner = FindViewAdapterFactory.fromActivity(owner);
    }

    public FieldInitializer(View view) {
        this.owner = FindViewAdapterFactory.fromView(view);
    }

    public static FieldInitializer getInstance(Activity owner) {
        return new FieldInitializer(owner);
    }

    public static FieldInitializer getInstance(View v) {
        return new FieldInitializer(v);
    }


    public FieldInitializer initTextField(int id, CharSequence cs) {
        ((TextView) owner.findViewById(id)).setText(cs);
        return this;
    }


    public FieldInitializer initTextField(int id, int value) {
        return initTextField(id, String.valueOf(value));
    }


}
