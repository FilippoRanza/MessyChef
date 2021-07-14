package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.HashSet;

class RadioButtonController {
    private RadioButton button;
    private int step;



    RadioButtonController(Activity owner, int step, ViewGroup group) {
        this.step = step;
        button = new RadioButton(owner);
        group.addView(button);

        button.setVisibility(RadioButton.INVISIBLE);
    }

    void update(int minutes, HashSet<Integer> hashSet) {
        int value = minutes / step;
        boolean show = value > 0 && hashSet.add(value);
        if (show) {
            button.setVisibility(RadioButton.VISIBLE);
            button.setText(convertTime(value));
        } else {
            button.setVisibility(RadioButton.INVISIBLE);
        }
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


}