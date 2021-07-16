package com.example.messychef.utils;

import android.app.Activity;
import android.widget.TextView;

public class FieldInitializer {

    private final Activity owner;



    public FieldInitializer(Activity owner) {
        this.owner = owner;
    }

    public static FieldInitializer getInstance(Activity owner) {
        return new FieldInitializer(owner);
    }

    public FieldInitializer initTextField(int id, CharSequence cs) {
        ((TextView) owner.findViewById(id)).setText(cs);
        return this;
    }


    public FieldInitializer initTextField(int id, int value) {
        return initTextField(id, String.valueOf(value));
    }





}
