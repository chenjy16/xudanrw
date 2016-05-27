package com.midea.trade.rws.exception;

public class DalException extends RuntimeException{
	
    private static final long serialVersionUID = 100010000001L;

    public DalException() {
    }

    public DalException(String msg) {
        super(msg);
    }

    public DalException(Throwable exception) {
        super(exception);
    }

    public DalException(String mag, Exception exception) {
        super(mag, exception);
    }

}
