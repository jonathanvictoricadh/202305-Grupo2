package com.jmg.checkagro.check.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckResponse {
    private Long id;
    private String documentTypeCustomer;
    private String documentValueCustomer;
    private String documentTypeProvider;
    private String documentValueProvider;
    private LocalDateTime emitDate;
    private BigDecimal amountTotal;
    private Integer monthsDuration;
    private BigDecimal commissionAgro;
    private String stateCheck;
    private Set<CheckResponse.CheckDetailResponse> checkDetails;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CheckDetailResponse {
        private Long id;
        private String concept;
        private BigDecimal amountUnit;
        private Integer quantity;
    }
}
