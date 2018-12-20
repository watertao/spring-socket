package net.watertao.springsock.infras.concurrent;

import java.util.concurrent.Callable;

public abstract class Call implements Callable<CallResult> {

    @Override
    public CallResult call() throws Exception {
        Object result = doCall();
        CallResult callResult = new CallResult();
        callResult.setSuccess(true);
        callResult.setResultPayload(result);
        return callResult;
    }

    protected abstract Object doCall() throws CallException;

}
