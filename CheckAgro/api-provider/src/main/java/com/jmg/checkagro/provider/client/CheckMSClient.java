package com.jmg.checkagro.provider.client;
import feign.Headers;
import feign.RequestLine;
import lombok.*;

import javax.validation.constraints.Size;

public interface CheckMSClient {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/v1/check/provider/register")
    void registerProvider(DocumentRequest request);

    @Headers("Content-Type: application/json")
    @RequestLine("POST /api/v1/check/provider/delete")
    void deleteProvider(DocumentRequest request);

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
