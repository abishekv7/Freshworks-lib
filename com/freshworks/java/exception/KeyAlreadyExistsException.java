package com.freshworks.java.exception;

public class KeyAlreadyExistsException extends Exception {
    private String reason;

    public KeyAlreadyExistsException(String reason) {
        this.reason = reason;
    }

    public String toString() {
        return reason;
    }
}
