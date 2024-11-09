package com.enalto.parallelism;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParallemismExample {

    public static void main(String[] args) {

        List<String> namesList = Arrays.asList("enalto", "juliana", "giovanna", "vitoria", "enaldinho");

        List<String> list = namesList.stream()
                .parallel()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        list.forEach(System.out::println);

        for(String listItem: list){
            System.out.println(listItem);
        }
    }


}
