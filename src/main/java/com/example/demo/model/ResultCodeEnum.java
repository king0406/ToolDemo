package com.example.demo.model;

/**
 * @author ：jyk
 * @date ：Created in 2020/4/6 21:08
 * @description：
 */
public enum ResultCodeEnum {

    SUCCESS(200),
    FAIL(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;

    ResultCodeEnum(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
