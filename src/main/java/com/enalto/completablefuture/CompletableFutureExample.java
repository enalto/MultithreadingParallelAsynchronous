package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import com.enalto.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {


    public static void main(String[] args) {
        final HelloWorldService helloWorldService = new HelloWorldService();

        CompletableFuture.supplyAsync(() -> helloWorldService.HelloWorld())
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> {
                    System.out.println("result " + result);
                }).join();


        //CommonUtil.delay(2000);
        System.out.println("done!");
    }
}
