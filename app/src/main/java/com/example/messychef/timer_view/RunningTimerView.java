package com.example.messychef.timer_view;

import android.widget.TextView;

class RunningTimerView extends TimerView {

    private final TextView timer;
    private final int startTime;

    RunningTimerView(TextView timer, int startTime) {
        this.startTime = startTime;
        this.timer = timer;
        reset();
    }

    @Override
    public void reset() {
        setTime(startTime);
    }

    @Override
    public void setTime(int time) {
        if (time >= 0) {
            String str = formatTime(time);
            timer.setText(str);
        }
    }

}
