package com.swwx.charm.commons.lang.exception;

public class BasicException extends Exception {

    private static final long serialVersionUID = 1L;

    public BasicException() {
        super();
    }

    public BasicException(String msg) {
        super(msg);
    }

    public BasicException(String msg, Throwable th) {
        super(msg, th);
    }

    public BasicException(Throwable th) {
        super(th);
    }
}
