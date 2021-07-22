package com.example.messychef.timer_service;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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
    public static final int SNOOZE = 3;


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
                case SNOOZE:
                    stopRingtone();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private static final long DEFAULT_SLEEP = 100;


    private final Timer timer;

    private final Messenger messenger;
    private Messenger clientMessenger;

    private int remainingGlobal;
    private int remainingStep;


    private long currentTime;

    private Ringtone ringtone;

    public TimerService() {
        super();
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
                timerEngine();
            }
        }, 0, DEFAULT_SLEEP);
    }

    private long getSeconds() {
        long millis = System.currentTimeMillis();
        return millis / 1000;
    }

    private void timerEngine() {
        long now = getSeconds();
        int delta = (int) (now - currentTime);
        if (delta > 0) {
            updateTime(delta, now);
            playRingtone();
            updateClient();
        }
    }

    private void updateTime(int delta, long now) {
        currentTime = now;
        remainingGlobal -= delta;
        remainingStep -= delta;
    }

    private void playRingtone() {
        if(remainingStep == 0 || remainingGlobal == 0) {
            startRingtone();
        }
    }

    private void startRingtone() {
        stopRingtone();
        ringtone.play();
    }

    private void stopRingtone() {
        if(ringtone == null)
            initRingtone();
        ringtone.stop();
    }

    private void initRingtone() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
    }

    private void updateClient() {
        if(clientMessenger != null)
            sendMessage();
    }

    private void sendMessage() {
        Message msg = Message.obtain(null, TIME_UPDATE, remainingGlobal, remainingStep);
        try {
            clientMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}