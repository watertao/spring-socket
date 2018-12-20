package net.watertao.springsock.infras.framework;

public abstract class AbstractAPIService<T> {

    public abstract APIResponse call(T request) throws APIException;

}
