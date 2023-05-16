package com.jmg.checkagro.customer.exception;

public class CustomerException extends Exception {

    public CustomerException(MessageCode exp){
        super(exp.getMsg());
    }
}
