package com.algosenpai.app.ui.controller;

import javafx.animation.AnimationTimer;

public abstract class AnimationTimerController extends AnimationTimer {

    private long sleepNs = 0;

    private long prevTime = 0;

    protected AnimationTimerController(long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now) {
        if ((now - prevTime) < sleepNs) {
            return;
        }
        prevTime = now;
        handle();
    }

    public abstract void handle();

}