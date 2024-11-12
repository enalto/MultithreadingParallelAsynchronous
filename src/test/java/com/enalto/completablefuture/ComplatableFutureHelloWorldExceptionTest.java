package com.enalto.completablefuture;

import com.enalto.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(" world hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionHandle2() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenThrow(new RuntimeException("Exception occurred! Hello"));
        Mockito.when(helloWorldService.World()).thenThrow(new RuntimeException("Exception occurred! World"));

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionHandle();

        //verification
        assertEquals(" hi completablefuture!", result);
    }

    @Test
    void helloWorldAsyncCallsExceptionHandle3() {
        //scenario
        Mockito.when(helloWorldService.Hello()).thenCallRealMethod();
        Mockito.when(helloWorldService.World()).thenCallRealMethod();

        //action
        String result = complatableFutureHelloWorldException.helloWorldAsyncCallsExceptionHandle();

        //verification
        assertEquals("hello world hi completablefuture!", result);
    }
}