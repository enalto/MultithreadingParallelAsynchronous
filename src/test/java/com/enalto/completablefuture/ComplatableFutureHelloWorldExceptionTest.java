package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComplatableFutureHelloWorldExceptionTest {


    @Mock
    private final HelloWorldService helloWorldService = Mockito.mock(HelloWorldService.class);

    @InjectMocks
    private ComplatableFutureHelloWorldException complatableFutureHelloWorldException;

    @Test
    void helloWorldAsyncCallsExceptionHandle() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenThrow(new RuntimeException("Exception occurred!"));
        Mockito.when(helloWorldService.World()).thenCallRealMethod();

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionHandle();

        //verification
        Assertions.assertEquals(" world hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionHandle2() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenThrow(new RuntimeException("Exception occurred! Hello"));
        Mockito.when(helloWorldService.World()).thenThrow(new RuntimeException("Exception occurred! World"));

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionHandle();

        //verification
        Assertions.assertEquals(" hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionHandle3() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenCallRealMethod();
        Mockito.when(helloWorldService.World()).thenCallRealMethod();

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionHandle();

        //verification
        Assertions.assertEquals("hello world hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionExceptionally() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenCallRealMethod();
        Mockito.when(helloWorldService.World()).thenCallRealMethod();

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionExceptionally();

        //verification
        Assertions.assertEquals("hello world hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionExceptionally2() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenThrow(new RuntimeException("Exception occurred! Hello"));
        Mockito.when(helloWorldService.World()).thenThrow(new RuntimeException("Exception occurred! World"));

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionExceptionally();

        //verification
        Assertions.assertEquals(" hi completablefuture!", result);
    }

}