package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.HashSet;

class RadioButtonController {
    final private RadioButton button;
    private int value;


    RadioButtonController(Activity owner, ViewGroup group) {
        button = new RadioButton(owner);
        group.addView(button);
        button.setVisibility(RadioButton.INVISIBLE);
    }

    void enable(int minutes) {
        button.setVisibility(RadioButton.VISIBLE);
        value = minutes;
        button.setText(convertTime(minutes));
    }

    void disable() {
        button.setVisibility(RadioButton.INVISIBLE);
    }



    RadioButtonController setListener(View.OnClickListener listener) {
        button.setOnClickListener(listener);
        return this;
    }

    void unselect() {
        button.setChecked(false);
    }

    private String convertTime(int value) {
        int h = value / 60;
        int m = value % 60;
        return String.format("%d:%02d", h, m);
    }


    public int getValue() {
        return value;
    }
}