package net.watertao.springsock.infras.framework;

import java.util.HashMap;
import java.util.Map;

public class APIMetadataHolder {

    private static final Map<String, APIMetadata> metadataMap = new HashMap<String, APIMetadata>();

    public static Map<String, APIMetadata> getMetadataMap() {
        return metadataMap;
    }

    public static void registerApi(String funcCode, AbstractAPIService service, Class requestClass) {
        if (metadataMap.get(funcCode) != null) {
            throw new IllegalStateException("Duplicated API function code [" + funcCode + "]");
        } else {
            metadataMap.put(funcCode, new APIMetadata(funcCode, service, requestClass));
        }
    }

    public static Class getRequestTypeViaFuncCode(String funcCode) {
        APIMetadata metadata = metadataMap.get(funcCode);
        if (metadata == null) {
            return null;
        } else {
            return metadata.getRequestClass();
        }
    }

    public static AbstractAPIService getServiceBeanViaFuncCode(String funcCode) {
        APIMetadata metadata = metadataMap.get(funcCode);
        if (metadata == null) {
            return null;
        } else {
            return metadata.getService();
        }
    }

}

class APIMetadata {

    private String functionCode;

    private Class requestClass;

    private AbstractAPIService service;

    APIMetadata(String functionCode, AbstractAPIService service, Class requestClass) {
        this.functionCode = functionCode;
        this.requestClass = requestClass;
        this.service = service;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public Class getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(Class requestClass) {
        this.requestClass = requestClass;
    }

    public AbstractAPIService getService() {
        return service;
    }

    public void setService(AbstractAPIService service) {
        this.service = service;
    }

}