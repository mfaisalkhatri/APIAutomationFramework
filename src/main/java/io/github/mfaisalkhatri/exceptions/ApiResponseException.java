package io.github.mfaisalkhatri.exceptions;

public class ApiResponseException extends RuntimeException {

    public ApiResponseException (final String message) {
        super (message);
    }

    public ApiResponseException (final String message, final Throwable cause) {
        super (message, cause);
    }
}
