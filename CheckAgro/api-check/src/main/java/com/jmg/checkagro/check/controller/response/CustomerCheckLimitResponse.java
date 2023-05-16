package com.jmg.checkagro.check.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerCheckLimitResponse {

    private BigDecimal checkAmountLimit;
    private BigDecimal checkAmountPayed;
    private BigDecimal checkAmountConsumed;
}
