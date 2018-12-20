package net.watertao.springsock.infras.concurrent;

public class CallResult {

    private boolean isSuccess;

    private String errMsg;

    private Object resultPayload;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getResultPayload() {
        return resultPayload;
    }

    public void setResultPayload(Object resultPayload) {
        this.resultPayload = resultPayload;
    }

}
