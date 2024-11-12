package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import com.enalto.util.CommonUtil;
import com.enalto.util.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class ComplatableFutureHelloWorldException {

    private final HelloWorldService helloWorldService;
    private static final Logger logger = Logger.getLogger(ComplatableFutureHelloWorldException.class.getName());


    public ComplatableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorldAsyncCallsExceptionHandle() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CompletableFuture<String> completableFutureHello = CompletableFuture.supplyAsync(() -> helloWorldService.Hello());
        CompletableFuture<String> completableFutureWorld = CompletableFuture.supplyAsync(() -> helloWorldService.World());

        CompletableFuture<String> completableFutureHi = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return " HI CompletableFuture!";
        });

        String hello = completableFutureHello
                .handle((response, exception) -> {
                    if (exception != null) {
                        logger.info(exception.getMessage());
                        return "";
                    } else {
                        return response;
                    }
                })
                .thenCombine(completableFutureWorld, (completableHello, completableWorld) -> completableHello + completableWorld)
                .handle((response, exception) -> {
                    if (exception != null) {
                        logger.info(exception.getMessage());
                        return "";
                    } else {
                        return response;
                    }
                })
                .thenCombine(completableFutureHi, (previous, current) -> previous + current)
                .thenApply(String::toLowerCase)
                .join();

        stopWatch.stop();
        System.out.println(stopWatch.getElapsedTime());
        return hello;

    }

}
