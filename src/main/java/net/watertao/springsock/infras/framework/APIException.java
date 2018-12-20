package net.watertao.springsock.infras.framework;

public class APIException extends Exception {

    private Integer code;
    private String msg;

    public APIException() {
        super(APIResponse.Status.INTERNAL_SERVER_ERR.getMsg());
        this.code = APIResponse.Status.INTERNAL_SERVER_ERR.getCode();
        this.msg = APIResponse.Status.INTERNAL_SERVER_ERR.getMsg();
    }

    public APIException(Throwable cause) {
        super(cause);
        this.code = APIResponse.Status.INTERNAL_SERVER_ERR.getCode();
        this.msg = APIResponse.Status.INTERNAL_SERVER_ERR.getMsg();
    }

    public APIException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public APIException(Integer code, String msg, Throwable cause) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
