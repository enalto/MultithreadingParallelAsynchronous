package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureExampleTest {

    private final HelloWorldService helloWorldService = new HelloWorldService();
    private final CompletableFutureExample completableFutureExample =
            new CompletableFutureExample(helloWorldService);

    @Test
    void helloWorld() {
        //scenario

        //action
        CompletableFuture<String> completableFuture = completableFutureExample.helloWorld();


        //verification
        completableFuture
                .thenAccept((result) -> Assertions.assertEquals("HELLO WORLD", result))
                .join();

    }

    @Test
    void helloWorld_withSize() {
        //scenario

        //action
        CompletableFuture<String> completableFuture = completableFutureExample.helloWorld_withSize();


        //verification
        completableFuture
                .thenAccept((result) -> Assertions.assertEquals("Hello World 11", result))
                .join();


    }

    @Test
    void hello_world_multipleAsync() {
        //scenario


        //action
        String result = completableFutureExample.hello_world_multipleAsync();

        //verification
        Assertions.assertEquals("HELLO WORLD HI COMPLETABLEFUTURE BYE!", result);

    }

    @Test
    void hello_world_multipleAsync_compose() {
        //scenario

        //action
        CompletableFuture<String> completableFuture = completableFutureExample.helloWorldCompose();

        //verification
        completableFuture.thenAccept(result -> {
            Assertions.assertEquals("HELLO WORLD COMPOSE!", result);
        }).join();
    }
}