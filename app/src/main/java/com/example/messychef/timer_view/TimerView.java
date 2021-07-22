package com.example.messychef.timer_view;

import android.view.View;
import android.widget.TextView;

abstract public class TimerView {

    public static TimerView getDisableInstance(View view, int id) {
        TextView textView = view.findViewById(id);
        textView.setVisibility(View.INVISIBLE);
        return new MockTimerView();
    }

    public static TimerView getInstance(View view, int id, int startTime) {
        TextView textView = view.findViewById(id);
        return new RunningTimerView(textView, startTime);
    }


    abstract public void reset();
    abstract public void setTime(int time);



    protected String formatTime(int seconds) {
        int h = seconds / 3600;
        seconds %= 3600;
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }



}
