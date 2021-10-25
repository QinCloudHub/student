package com.management.app.support;


/**
 * Restful 接口包装
 */
public class Rest<D> {

    /**
     * 数据
     */
    private D data;

    /**
     * 响应码
     */
    private int code;

    /**
     * 是否执行成功
     */
    private boolean success;

    /**
     * 信息
     */
    private String message;

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rest() {
    }

    public Rest(D data, int code, boolean success, String message) {
        this.data = data;
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public Rest(D data, State state) {
        this.data = data;
        this.code = state.getCode();
        this.message = state.getMessage();
        if (this.code >= 200 && this.code < 300) {
            this.success = true;
        } else {
            this.success =false;
        }
    }

    public Rest(State state) {
        this.code = state.getCode();
        this.message = state.getMessage();
        if (this.code >= 200 && this.code < 300) {
            this.success = true;
        } else {
            this.success =false;
        }
    }

    enum State {
        /**
         * 执行成功
         */
        SUCCESS(200, "执行成功"),

        /**
         * 执行失败
         */
        FAILURE(300, "执行失败"),

        /**
         *
         */
        REFUSED(301, "授权失败");

        int code;

        String message;

        State(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static <D> Rest<D> ok() {
        return new Rest<D>(null, State.SUCCESS);
    }

    public static <D> Rest<D> ok(D data) {
        return new Rest<D>(data, State.SUCCESS);
    }

    public static <D> Rest<D> failure(D data) {
        return new Rest<D>(data, State.FAILURE);
    }

    public static <D> Rest<D> refused(D data) {
        return new Rest<D>(data, State.REFUSED);
    }
}
