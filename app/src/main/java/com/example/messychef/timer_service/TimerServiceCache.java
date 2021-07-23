package com.example.messychef.timer_service;


class TimerServiceCache {

    private static TimerServiceCache instance;
    private TimerService timerService;

    private TimerServiceCache() {
    }

    public static TimerServiceCache getInstance() {
        if(instance == null)
            instance = new TimerServiceCache();
        return instance;
    }

    public TimerService getTimerService() {
        return timerService;
    }

    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
    }
}
