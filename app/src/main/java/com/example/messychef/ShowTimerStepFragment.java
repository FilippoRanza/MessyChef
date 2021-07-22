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
import com.example.messychef.utils.FieldInitializer;


public  class ShowTimerStepFragment extends AbstractShowStepFragment {


    private boolean bound;
    private final ServiceConnection connection;
    private final Messenger messenger;

    private final Activity owner;
    private final RecipeTimer timer;

    private final Intent timerIntent;

    private FieldInitializer initializer;

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
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_show_timer_step, container, false);
        initializer = FieldInitializer.getInstance(inflate);
        initView(inflate);
        return inflate;
    }

    private void initView(View v) {
        initializer
                .initTextField(R.id.global_timer_clock, formatTime(timer.getGlobalTime()));
        initStepTimeField(v);
        initializeButtons(v);
    }

    private void initStepTimeField(View v) {
        if (timer.hasStepTime())
            setStepTimerValue();
        else
            hideStepTimer(v);
    }

    private void setStepTimerValue() {
        initializer.initTextField(R.id.step_timer_clock, formatTime(timer.getStepTime()));
    }

    private void hideStepTimer(View v) {
        TextView textView = v.findViewById(R.id.step_timer_clock);
        textView.setVisibility(View.INVISIBLE);
    }
    
    private String formatTime(int seconds) {
        int h = seconds / 3600;
        seconds %= 3600;
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
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
    }

    private void startButtonClick(View v) {
        owner.startService(timerIntent);
        owner.bindService(timerIntent, connection, Context.BIND_AUTO_CREATE);

    }

    private void stopButtonClick(View v) {
        if(bound) {
            owner.unbindService(connection);
            owner.stopService(timerIntent);
            bound = false;
        }
    }

    private ServiceConnection initServiceConnection() {
        return new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName className,
                                           IBinder service) {
                Messenger serviceListener = new Messenger(service);
                Message msg1 = Message.obtain(null, TimerService.SET_GLOBAL_TIME,  ShowTimerStepFragment.this.timer.getGlobalTime(), 0);
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
                initializer.initTextField(R.id.global_timer_clock, formatTime(msg.arg1));
                initializer.initTextField(R.id.step_timer_clock, formatTime(msg.arg2));
            } else {
                super.handleMessage(msg);
            }
        }
    }

}