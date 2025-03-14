package com.swwx.charm.commons.lang.exception;

public class SystemErrorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SystemErrorException() {
        super();
    }

    public SystemErrorException(String msg) {
        super(msg);
    }

    public SystemErrorException(String msg, Throwable th) {
        super(msg, th);
    }

    public SystemErrorException(Throwable th) {
        super(th);
    }
}
