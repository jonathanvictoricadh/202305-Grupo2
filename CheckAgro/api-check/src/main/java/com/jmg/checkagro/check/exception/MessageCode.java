package com.jmg.checkagro.check.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageCode {
    CHECK_NOT_FOUND("The check not found"),
    CHECK_NOT_ACTIVE("The check not active"),
    CHECK_CUSTOMER_NOT_FOUND("The customer not found"),
    CHECK_PROVIDER_NOT_FOUND("The provider not found"),
    CUSTOMER_NOT_LIMIT("Limit exceeded"), CHECK_NOT_TOTAL_AMOUNT_EQUALS("Verify details amounts");

    String msg;


}
