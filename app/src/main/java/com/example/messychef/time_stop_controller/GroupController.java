package com.example.messychef.time_stop_controller;

import android.app.Activity;
import android.view.ViewGroup;

import java.util.HashSet;

public class GroupController {

    private static final int NONE_SELECTION = -1;

    final private RadioButtonController[] buttons;
    final private int[] scaleValues;
    final private HashSet<Integer> values;
    private int selected;

    public GroupController(int count, Activity owner, ViewGroup layout) {
        values = new HashSet<>();
        selected = NONE_SELECTION;
        buttons = new RadioButtonController[count];
        scaleValues = new int[count];
        for (int i = 0; i < count; i++) {
            buttons[i] = initialize(i, owner, layout);
            scaleValues[i] = i + 2;
        }

    }

    public void update(int minutes) {
        computeSteps(minutes);
        Integer[] steps = getSortedStepValues();
        fixSelection(steps.length);
        updateButtons(steps);
    }

    public Integer getSelectedTime() {
        return (selected == NONE_SELECTION) ? null : buttons[selected].getValue();
    }

    public void clear() {
        if (selected != NONE_SELECTION)
            buttons[selected].unselect();
        selected = NONE_SELECTION;
    }

    private void computeSteps(int minutes) {
        values.clear();
        for (int v : scaleValues) {
            int m = minutes / v;
            if (m > 0) {
                m = roundToFive(m);
                values.add(m);
            }
        }
    }

    private void fixSelection(int enableLen) {
        if (enableLen == 0) {
            clear();
        }

        if (selected >= enableLen) {
            buttons[selected].unselect();
            selected = NONE_SELECTION;
        }
    }

    private Integer[] getSortedStepValues() {
        return values.stream().sorted().toArray(Integer[]::new);
    }

    private void updateButtons(Integer[] steps) {
        for (int i = 0; i < buttons.length; i++) {
            if (i < steps.length) {
                buttons[i].enable(steps[i]);
            } else {
                buttons[i].disable();
            }
        }
    }

    private RadioButtonController initialize(int id, Activity owner, ViewGroup layout) {

        return new RadioButtonController(owner, layout).setListener((v) -> {
            if (selected != NONE_SELECTION) {
                buttons[selected].unselect();
            }
            selected = id;
            System.out.println(selected);
        });
    }

    private int roundToFive(int a) {
        if (a < 5) {
            return a;
        }
        int m = a % 5;
        a -= m;
        if (m > 3) {
            a += 5;
        }
        return a;
    }


}
