package com.enalto.paralellstream;

import com.enalto.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(List<Integer> list, Integer multiplyValue) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Integer> resultList = list.parallelStream()
                .map(integer -> integer * multiplyValue)
                .collect(Collectors.toList());

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return resultList;


    }
}
