package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import com.enalto.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    private final HelloWorldService helloWorldService;

    public CompletableFutureExample(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public static void main(String[] args) {

    }

    public String hello_world_multipleAsync() {

        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> helloWorldService.Hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> helloWorldService.World());
        CompletableFuture<String> hicompletable = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Hi completableFuture";
        });
        CompletableFuture<String> bye = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Bye!";
        });


        String join = hello.thenCombine(world, (h, w) -> h + w)
                .thenCombine(hicompletable, (previous, current) -> previous + " " + current)
                .thenCombine(bye, (previous, current) -> previous + " " + current)
                .thenApply(String::toUpperCase)
                .join();

        return join;

    }

    public CompletableFuture<String> helloWorld() {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> helloWorldService.HelloWorld())
                .thenApply(String::toUpperCase);

        return completableFuture;

    }

    public CompletableFuture<String> helloWorld_withSize() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> helloWorldService.HelloWorld())
                .thenApply((result) -> result + " " + result.length());

        return completableFuture;
    }

    public CompletableFuture<String> helloWorldCompose() {

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> helloWorldService.Hello())
                .thenCompose((previous) -> helloWorldService.WorldAsync(previous))
                .thenApply(String::toUpperCase);

        return completableFuture;

    }

}
