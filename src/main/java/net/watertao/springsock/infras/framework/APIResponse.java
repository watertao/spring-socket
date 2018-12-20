package net.watertao.springsock.infras.framework;


/**
 * Generic response class.
 * The api could inherit this class with customer fields. The recommended field is defining a [payload] field.
 *
 * Response Code:
 * 2XX（successful）
 *  200 - OK
 *  201 - Created
 *  202 - Accepted
 *  204 - No Content
 * 4XX（request error）
 *  400 - Bad Request
 *  404 - Not Found
 *  405 - Api Not Found
 *  409 - Conflict
 * 5xx（server error）
 *  500 - Internal Server Error
 *
 * The response code definition is referenced to HTTP status code
 */
public class APIResponse {

    private Integer code;

    private String msg;


    public APIResponse() {
        this.code = Status.OK.code;
        this.msg = Status.OK.msg;
    }

    public APIResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResponse(Status status) {
        this.code = status.code;
        this.msg = status.msg;
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


    public static enum Status {

        OK(200, "OK"),
        CREATED(201, "Created"),
        ACCEPTED(202, "Accepted"),
        NO_CONTENT(204, "No Content"),

        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        API_NOT_FOUND(405, "Api Not Found"),
        CONFILICT(409, "Conflict"),

        INTERNAL_SERVER_ERR(500, "Internal Server Error");

        private Integer code;
        private String msg;

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

        private Status(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

}
