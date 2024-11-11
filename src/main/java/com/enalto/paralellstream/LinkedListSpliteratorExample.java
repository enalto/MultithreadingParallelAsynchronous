package com.enalto.paralellstream;

import com.enalto.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListSpliteratorExample {

    public List<Integer> multiplyEachValue(List<Integer> list, Integer multiplyValue, boolean parallel) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Stream<Integer> stream = list.stream();

        if (parallel)
            stream.parallel();

        List<Integer> resultList = stream
                .map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return resultList;

    }
}
