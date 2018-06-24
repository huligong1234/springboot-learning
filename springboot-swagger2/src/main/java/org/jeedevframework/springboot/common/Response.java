package org.jeedevframework.springboot.common;

public class Response<T> {
	private static final String SUCCESS_CODE = "2000";
    private static final String FAILURE_CODE = "4000"; //通用业务失败代码

    private String code;
    private String message;
    private T data;

    public static <T> Response<T> succeed(T data) {
        Response<T> response = new Response<>();
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> succeed(T data, String message) {
        Response<T> response = new Response<>();
        response.setCode(SUCCESS_CODE);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static Response<String> fail(String message) {
        Response<String> response = new Response<String>();
        response.setCode(FAILURE_CODE);
        response.setMessage(message);
        return response;
    }

    public static Response<String> fail(String code, String message) {
    	Response<String> response = new Response<String>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static Response<String> fail(String code, String message,String data) {
    	Response<String> response = new Response<String>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
