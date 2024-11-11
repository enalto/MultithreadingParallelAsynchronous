package com.enalto.service;

import com.enalto.util.CommonUtil;
import com.enalto.util.StopWatch;

import java.util.concurrent.CompletableFuture;

public class HelloWorldService {

    private StopWatch stopWatch = new StopWatch();

    public String HelloWorld() {
        System.out.println("inside HelloWorldService");
        CommonUtil.delay(1000);
        return "Hello World";
    }

    public String Hello() {
        CommonUtil.delay(1000);
        return "Hello";
    }

    public String World() {
        CommonUtil.delay(1000);
        return " World";
    }

    public CompletableFuture<String> WorldAsync(String input) {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return input + " World Compose!";
        });
        return completableFuture;

    }


}
