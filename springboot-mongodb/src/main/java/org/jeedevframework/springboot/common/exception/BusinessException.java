package org.jeedevframework.springboot.common.exception;

/**
 * 用于封装业务类型的异常
 */
public class BusinessException extends RuntimeException {
    String code;

    public String getCode() {
        return code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public BusinessException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

}
