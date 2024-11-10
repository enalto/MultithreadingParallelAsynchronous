package com.enalto.util;

public class StopWatch {
    private long startTime;
    private long stopTime;

    public StopWatch() {
        startTime = System.currentTimeMillis();
        stopTime = System.currentTimeMillis();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }
    public void stop() {
        stopTime = System.currentTimeMillis();
    }
    public long getElapsedTime() {
        return stopTime - startTime;
    }
}
