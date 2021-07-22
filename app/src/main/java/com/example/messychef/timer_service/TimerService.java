package com.example.messychef.timer_service;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.messychef.R;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    public class TimerController extends Binder {

        public void setGlobalTime(int time) {
            remainingGlobal = time;
        }

        public void setStepTime(int time) {
            remainingStep = time;
        }

        public void installMessenger(UpdateTimer update) {
            updateTime = update;
        }

        public void snooze() {
            stopRingtone();
        }

        public void start() {
            running = true;
        }

        public void pause() {
            running = false;
        }

    }

    private static final long DEFAULT_SLEEP = 100;


    private final Timer timer;

    private final IBinder binder;
    private UpdateTimer updateTime;

    private int remainingGlobal;
    private int remainingStep;


    private long currentTime;

    private Ringtone ringtone;

    private boolean running;

    public TimerService() {
        super();
        this.timer = new Timer();
        binder = new TimerController();
        running = false;
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
        return binder;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, R.string.stop_timer_service, Toast.LENGTH_SHORT).show();
        timer.cancel();
        stopRingtone();
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
        if (running) {
            if (delta > 0) {
                updateTime(delta);
                playRingtone();
                updateClient();
            }
        }
        currentTime = now;
    }

    private void updateTime(int delta) {
        remainingGlobal -= delta;
        remainingStep -= delta;
    }

    private void playRingtone() {
        if (remainingStep == 0 || remainingGlobal == 0) {
            startRingtone();
        }
    }

    private void startRingtone() {
        stopRingtone();
        ringtone.play();
    }

    private void stopRingtone() {
        if (ringtone == null)
            initRingtone();
        ringtone.stop();
    }

    private void initRingtone() {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, uri);
    }

    private void updateClient() {
        if (updateTime != null)
            sendMessage();
    }

    private void sendMessage() {
        updateTime.updateTimer(remainingGlobal, remainingStep);
    }
}