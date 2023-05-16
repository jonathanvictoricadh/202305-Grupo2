package com.jmg.checkagro.check.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderCheckLimitResponse {

    private BigDecimal checkAmountReceived;
    private BigDecimal checkAmountActive;
}
