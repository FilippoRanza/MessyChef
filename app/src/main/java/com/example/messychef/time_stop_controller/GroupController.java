package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.view.ViewGroup;

import java.util.HashSet;

public class GroupController {

    final private RadioButtonController[] buttons;
    final private HashSet<Integer> values;
    final private ViewGroup group;

    public GroupController(int count, Activity owner, ViewGroup layout) {
        values = new HashSet<>();
        this.group = layout;
        buttons = new RadioButtonController[count];
        for(int i = 0; i < count; i++) {
            buttons[i] = initialize(i + 2, owner, layout);
        }
    }

    public void update(int minutes) {
        values.clear();
        //group.removeAllViews();
        for(RadioButtonController rbc: buttons) {
            rbc.update(minutes, values);
        }

    }

    private RadioButtonController initialize(int step, Activity owner, ViewGroup layout) {
        return new RadioButtonController(owner, step, layout);
    }



}
