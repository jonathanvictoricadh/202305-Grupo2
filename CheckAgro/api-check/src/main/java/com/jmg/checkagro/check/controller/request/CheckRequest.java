package com.jmg.checkagro.check.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckRequest {
    @Size(min = 1, max = 10)
    private String documentTypeCustomer;
    @Size(min = 1, max = 20)
    private String documentValueCustomer;
    @Size(min = 1, max = 10)
    private String documentTypeProvider;
    @Size(min = 1, max = 20)
    private String documentValueProvider;
    @NotNull()
    @Digits(integer = 15, fraction = 2)
    private BigDecimal amountTotal;
    @Positive
    private Integer monthsDuration;
    @NotEmpty
    private Set<CheckDetailRequest> checkDetails;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CheckDetailRequest {
        @Size(min = 1, max = 250)
        private String concept;
        @NotNull()
        @Digits(integer = 15, fraction = 2)
        private BigDecimal amountUnit;
        @Positive
        private Integer quantity;
    }
}
