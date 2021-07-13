package com.example.messychef.utils;

public class SelectedIndex {

    private final int value;
    private boolean selected;

    public SelectedIndex(int value) {
        this.value = value;
        selected = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
