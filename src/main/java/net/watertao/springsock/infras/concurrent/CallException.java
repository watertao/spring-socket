package net.watertao.springsock.infras.concurrent;

public class CallException extends Exception {

    public CallException(String msg) {
        super(msg);
    }

    public CallException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
