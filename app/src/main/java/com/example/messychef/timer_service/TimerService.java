package com.example.messychef.timer_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.messychef.R;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    public static final int SET_GLOBAL_TIME = 0;
    public static final int SET_LOCAL_TIME = 1;
    public static final int INSTALL_LISTENER = 2;
    public static final int TIME_UPDATE = 0;


    private class TimerHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case SET_GLOBAL_TIME:
                    remainingGlobal = msg.arg1;
                    break;
                case SET_LOCAL_TIME:
                    remainingStep = msg.arg1;
                    break;
                case INSTALL_LISTENER:
                    clientMessenger = msg.replyTo;
                    break;
                default:
                    super.handleMessage(msg);
            }
            System.out.println(remainingGlobal);
            System.out.println(remainingStep);
        }
    }

    private static final long DEFAULT_SLEEP = 100;


    private final Timer timer;

    private final Messenger messenger;
    private Messenger clientMessenger;

    private int remainingGlobal;
    private int remainingStep;


    private long currentTime;



    public TimerService() {
        this.timer = new Timer();
        messenger = new Messenger(new TimerHandler());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, R.string.start_timer_service, Toast.LENGTH_SHORT).show();

        startTimer();
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, R.string.stop_timer_service, Toast.LENGTH_SHORT).show();
        timer.cancel();
    }


    private void startTimer() {
        currentTime = getSeconds();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long now = getSeconds();
                int delta = (int) (now - currentTime);
                if (delta > 0) {
                    currentTime = now;
                    if(clientMessenger == null)
                        return;
                    remainingGlobal -= delta;
                    remainingStep -= delta;
                    try {
                        clientMessenger.send(Message.obtain(null, TIME_UPDATE, remainingGlobal, remainingStep));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0, DEFAULT_SLEEP);
    }

    private long getSeconds() {
        long millis = System.currentTimeMillis();
        return millis / 1000;
    }

}