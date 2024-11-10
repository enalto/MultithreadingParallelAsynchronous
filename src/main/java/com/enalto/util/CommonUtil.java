package com.enalto.util;

public class CommonUtil {

    public static StopWatch stopWatch = new StopWatch();

    public static void delay(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
