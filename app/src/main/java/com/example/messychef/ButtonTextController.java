package com.example.messychef;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ButtonTextController {

    Button button;
    ArrayList<Integer> textIds;
    int current;

    public ButtonTextController(View v, int id) {
        this();
        button = v.findViewById(id);
    }


    public ButtonTextController(Activity c, int id) {
        this();
        button = c.findViewById(id);
    }

    private ButtonTextController() {
        current = 0;
        textIds = new ArrayList<>();
    }


    public void reset() {
        current = 0;
        nextText();
    }

    public ButtonTextController addStringId(int id) {
        textIds.add(id);
        return this;
    }

    public void nextText(){
        int id = textIds.get(current);
        current = (current + 1) % textIds.size();
        button.setText(id);
    }

}
