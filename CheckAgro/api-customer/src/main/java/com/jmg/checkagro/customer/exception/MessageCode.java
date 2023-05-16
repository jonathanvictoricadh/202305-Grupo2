package com.jmg.checkagro.customer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageCode {
    CUSTOMER_NOT_FOUND("The customer not found"),
    CUSTOMER_EXISTS("The customer exists by document type and document number");

    String msg;


}
