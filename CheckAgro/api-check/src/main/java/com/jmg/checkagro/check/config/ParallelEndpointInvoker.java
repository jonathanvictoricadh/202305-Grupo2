package com.jmg.checkagro.check.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelEndpointInvoker {

    private static final int NUM_REQUESTS = 100;

    private static final  RestTemplate restTemplate = new RestTemplate();
    private static final String ENDPOINT_URL = "http://localhost:8089/api/v1/provider";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        for(int i=1200;i<25000;i++){
            invokeEndpointAsync("20514569"+i);
//            TimeUnit.MILLISECONDS.sleep(200);
        }
    }


    private static void invokeEndpointAsync(String document) {
        ProviderRequest request = new ProviderRequest("DNI",document,"Generico","nose","15151");
        HttpEntity<ProviderRequest> requestEntity = new HttpEntity<>(request); // Replace "request body" with your actual request body
        restTemplate.exchange(ENDPOINT_URL, HttpMethod.POST, requestEntity, Map.class );
    }


     @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProviderRequest {

        @Size(min = 1, max = 10)
        private String documentType;
        @Size(min = 1, max = 20)
        private String documentNumber;
        @Size(min = 1, max = 100)
        private String businessName;
        @Size(min = 1, max = 250)
        private String email;
        @Size(min = 1, max = 20)
        private String phone;
    }


}