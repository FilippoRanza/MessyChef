package com.example.messychef;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.timer_service.TimerService;
import com.example.messychef.timer_service.UpdateTimer;
import com.example.messychef.timer_view.TimerView;


public class ShowTimerStepFragment extends AbstractShowStepFragment {

    public class ShowTimerController extends UpdateTimer {
        @Override
        public void updateTimer(int global, int step) {
            globalTimer.setTime(global);
            stepTimer.setTime(step);
        }

        @Override
        public void done() {
            startButton.reset();
        }

        @Override
        public void timeout() {
        }

    }

    private boolean bound;
    private final ServiceConnection connection;
    private TimerService.TimerController controller;

    private final Activity owner;
    private final RecipeTimer timer;

    private final Intent timerIntent;


    private TimerView globalTimer;
    private TimerView stepTimer;

    private ButtonTextController startButton;

    private enum Status {
        Init,
        Running,
        Pause
    }

    private Status status;

    public ShowTimerStepFragment(Activity owner) {
        this.owner = owner;
        timer = RecipeRunner.getInstance().getStep();
        timerIntent = new Intent(owner, TimerService.class);
        connection = initServiceConnection();
        status = Status.Init;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_show_timer_step, container, false);

        initView(inflate);
        return inflate;
    }

    private void initView(View v) {
        findTimerTextViews(v);
        initializeButtons(v);
    }

    private void findTimerTextViews(View v) {
        globalTimer = TimerView.getInstance(v, R.id.global_timer_clock, timer.getGlobalTime());
        if (timer.hasStepTime())
            stepTimer = TimerView.getInstance(v, R.id.step_timer_clock, timer.getStepTime());
        else
            stepTimer = TimerView.getDisableInstance(v, R.id.step_timer_clock);
    }

    private void initializeButtons(View v) {
        setOnClick(v, R.id.start_pause_button, this::startButtonClick);
        setOnClick(v, R.id.button_stop, this::stopButtonClick);
        //setOnClick(v, R.id.snooze_button, this::snoozeTimer);
        startButton = new ButtonTextController(v, R.id.start_pause_button)
                .addStringId(R.string.start_timer_button)
                .addStringId(R.string.pause_timer_button);
        startButton.reset();
    }

    private void setOnClick(View view, int id, View.OnClickListener run) {
        Button button = view.findViewById(id);
        button.setOnClickListener(run);
    }


    private void startButtonClick(View v) {
        switch (status) {
            case Init:
                initService();
                status = Status.Running;
                break;
            case Pause:
                restartTimer();
                status = Status.Running;
                break;
            case Running:
                pauseTimer();
                status = Status.Pause;
                break;
        }
        startButton.nextText();
    }


    private void initService() {
        owner.startService(timerIntent);
        owner.bindService(timerIntent, connection, Context.BIND_AUTO_CREATE);
    }

    private void pauseTimer() {
        if (bound)
            controller.pause();
    }

    private void restartTimer() {
        if (bound)
            controller.start();
    }


    private void stopButtonClick(View v) {
        if (bound) {
            owner.unbindService(connection);
            owner.stopService(timerIntent);
            bound = false;
            globalTimer.reset();
            stepTimer.reset();
            status = Status.Init;
            startButton.reset();
        }
    }

    private ServiceConnection initServiceConnection() {
        return new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                controller = (TimerService.TimerController) service;
                controller.setGlobalTime(timer.getGlobalTime());
                controller.setStepTime(timer.getStepTime());
                controller.installMessenger(new ShowTimerController());
                controller.start();
                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bound = false;
            }
        };
    }

}