package com.jmg.checkagro.provider.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageCode {
    PROVIDER_NOT_FOUND("The provider not found"),
    PROVIDER_EXISTS("The provider exists by document type and document number");

    String msg;


}
