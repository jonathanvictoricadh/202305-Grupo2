package com.jmg.checkagro.customer.client;
import feign.Headers;
import feign.RequestLine;
import lombok.*;
import org.springframework.cloud.openfeign.FeignClient;

import javax.validation.constraints.Size;
@FeignClient(name = "api-check")
public interface CheckMSClient {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/v1/check/customer/register")
    void registerCustomer(DocumentRequest request);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/v1/check/customer/delete")
    void deleteCustomer(DocumentRequest request);

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    class DocumentRequest {
        @Size(min = 1, max = 10)
        private String documentType;
        @Size(min = 1, max = 20)
        private String documentValue;
    }

}
