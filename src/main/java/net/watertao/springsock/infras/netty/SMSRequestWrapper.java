package net.watertao.springsock.infras.netty;

public class SMSRequestWrapper {

    private String functionCode;

    private Object requestPayload;

    private String payload;


    public SMSRequestWrapper(String functionCode, Object requestPayload, String payload) {
        this.functionCode = functionCode;
        this.requestPayload = requestPayload;
        this.payload = payload;
    }


    public String getFunctionCode() {
        return functionCode;
    }

    public Object getRequestPayload() {
        return requestPayload;
    }

    public String getPayload() {
        return payload;
    }

}
