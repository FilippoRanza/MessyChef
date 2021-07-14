package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.HashSet;

public class GroupController {

    private static final int NONE_SELECTION = -1;

    final private RadioButtonController[] buttons;
    final private HashSet<Integer> values;
    final private ViewGroup group;
    private int selected;

    public GroupController(int count, Activity owner, ViewGroup layout) {
        values = new HashSet<>();
        this.group = layout;
        selected = NONE_SELECTION;
        buttons = new RadioButtonController[count];
        for(int i = 0; i < count; i++) {
            buttons[i] = initialize(i, i + 2, owner, layout);
        }
    }

    public void update(int minutes) {
        values.clear();
        //group.removeAllViews();
        for(RadioButtonController rbc: buttons) {
            rbc.update(minutes, values);
        }


    }

    private RadioButtonController initialize(int id, int step, Activity owner, ViewGroup layout) {

        return new RadioButtonController(owner, step, layout).setListener((v) -> {
            if(selected != NONE_SELECTION) {
                buttons[selected].unselect();
            }
            selected = id;
            System.out.println(selected);
        });
    }



}
