package com.company.search.exceptions;

public class TruProxyServiceException extends RuntimeException {
    public TruProxyServiceException(String message) {
        super(message);
    }

    public TruProxyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
