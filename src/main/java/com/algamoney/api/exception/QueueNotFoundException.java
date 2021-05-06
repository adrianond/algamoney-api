package com.algamoney.api.exception;

public class QueueNotFoundException extends RuntimeException {
    public QueueNotFoundException(String s) {
        super(s);
    }
}