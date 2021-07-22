package com.example.messychef;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.messychef.recipe.RecipeRunner;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.timer_service.TimerService;
import com.example.messychef.timer_view.TimerView;
import com.example.messychef.utils.FieldInitializer;

import java.sql.Time;


public class ShowTimerStepFragment extends AbstractShowStepFragment {


    private boolean bound;
    private final ServiceConnection connection;
    private final Messenger messenger;
    private Messenger serviceListener;

    private final Activity owner;
    private final RecipeTimer timer;

    private final Intent timerIntent;


    private TimerView globalTimer;
    private TimerView stepTimer;

    public ShowTimerStepFragment(Activity owner) {
        this.owner = owner;
        timer = RecipeRunner.getInstance().getStep();
        timerIntent = new Intent(owner, TimerService.class);
        connection = initServiceConnection();
        messenger = new Messenger(new TimeHandler());
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
        setOnClick(v, R.id.snooze_button, this::snoozeTimer);
    }

    private void setOnClick(View view, int id, View.OnClickListener run) {
        Button button = view.findViewById(id);
        button.setOnClickListener(run);
    }

    private void snoozeTimer(View v) {
        if(!bound)
            return;
        Message msg = Message.obtain(null, TimerService.SNOOZE, 0, 0);
        try {
            serviceListener.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void startButtonClick(View v) {
        owner.startService(timerIntent);
        owner.bindService(timerIntent, connection, Context.BIND_AUTO_CREATE);

    }

    private void stopButtonClick(View v) {
        if (bound) {
            owner.unbindService(connection);
            owner.stopService(timerIntent);
            bound = false;
            globalTimer.reset();
            stepTimer.reset();
        }
    }

    private ServiceConnection initServiceConnection() {
        return new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                serviceListener = new Messenger(service);
                Message msg1 = Message.obtain(null, TimerService.SET_GLOBAL_TIME, ShowTimerStepFragment.this.timer.getGlobalTime(), 0);
                Message msg2 = Message.obtain(null, TimerService.SET_LOCAL_TIME, ShowTimerStepFragment.this.timer.getStepTime(), 0);
                Message msg3 = Message.obtain(null, TimerService.INSTALL_LISTENER);
                msg3.replyTo = ShowTimerStepFragment.this.messenger;
                try {
                    serviceListener.send(msg1);
                    serviceListener.send(msg2);
                    serviceListener.send(msg3);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName arg0) {
                bound = false;
            }
        };
    }


    private class TimeHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == TimerService.TIME_UPDATE) {
                globalTimer.setTime(msg.arg1);
                stepTimer.setTime(msg.arg2);
            } else {
                super.handleMessage(msg);
            }
        }
    }

}