package com.freshworks.java.exception;

public class FileSizeException extends Exception {
    private String reason;

    public FileSizeException(String reason) {
        this.reason = reason;
    }

    public String toString() {
        return reason;
    }
}
