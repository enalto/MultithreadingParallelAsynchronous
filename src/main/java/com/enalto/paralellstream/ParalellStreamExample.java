package com.enalto.paralellstream;

import com.enalto.util.CommonUtil;
import com.enalto.util.StopWatch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParalellStreamExample {


    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch();


        List<String> list = Arrays.asList("enalto", "juliana", "giovanna", "vitoria", "enaldinho");

        stopWatch.start();
        ParalellStreamExample paralellStreamExample = new ParalellStreamExample();
        List<String> stringList = paralellStreamExample.stringTransform(list);
        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        System.out.println(stringList);
    }

    private String addNameLength(String name) {
        CommonUtil.delay(1000);

        return name.length() + " " + name;
    }

    public List<String> stringTransform(List<String> list) {
        return list.stream()
                .parallel()
                .map(this::addNameLength)
                .collect(Collectors.toList());

    }
}
