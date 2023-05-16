package com.jmg.checkagro.check.exception;

public class CheckException extends Exception {

    public CheckException(MessageCode exp) {
        super(exp.getMsg());
    }
}
