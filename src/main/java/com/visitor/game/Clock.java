package com.visitor.game;

public class Clock extends Thread {

    enum ClockStatus {
        ACTIVE, PAUSED
    }


    ClockStatus clockStatus;
    int timeLeft;
    Runnable callback;


    public Clock(int setTimeSeconds, Runnable callback) {
        clockStatus = ClockStatus.PAUSED;
        timeLeft = setTimeSeconds * 1000;
        this.callback = callback;
    }

    @Override
    public void run() {
        while (timeLeft > 0) {
            try {
                Thread.sleep(Math.min(timeLeft, 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isActive()) {
                timeLeft -= 1000;
            }

            if (timeLeft <= 0) {
                pause();
                callback.run();
            }
        }
    }

    public void pause() {
        clockStatus = ClockStatus.PAUSED;
    }

    public void activate() {
        clockStatus = ClockStatus.ACTIVE;
    }

    public boolean isActive() {
        return clockStatus == ClockStatus.ACTIVE;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
