package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import com.enalto.util.CommonUtil;
import com.enalto.util.StopWatch;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }


    public String helloWorldAsyncCalls() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.Hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.World());

        CompletableFuture<String> completableFutureHi = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "HI CompletableFuture!";
        });

        String hello = completableFutureHello
                .thenCombine(completableFutureWorld, (completableHello, completableWorld) -> completableHello + completableWorld)
                .thenCombine(completableFutureHi, (previous, current) -> previous + current)
                .thenApply(String::toLowerCase)
                .join();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return hello;

    }

}
