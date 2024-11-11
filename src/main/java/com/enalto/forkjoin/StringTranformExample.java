package com.enalto.forkjoin;

import com.enalto.util.CommonUtil;
import com.enalto.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringTranformExample {
    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<String> list = Arrays.asList("enalto", "juliana", "giovanna", "vitoria", "enaldinho");
        List<String> result = new ArrayList<>();

        list.forEach(name -> {
            String newName = addNameLength(name);
            result.add(newName);
        });

        stopWatch.stop();
        System.out.println("list result= " + result);
        System.out.println(stopWatch.getElapsedTime());

    }

    private static String addNameLength(String name) {
        CommonUtil.delay(1000);

        return name.length() + " " + name;
    }

}
