package com.jmg.checkagro.provider.exception;

public class ProviderException extends Exception {

    public ProviderException(MessageCode exp){
        super(exp.getMsg());
    }
}
