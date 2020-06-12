package com.example.demo.model;

import com.alibaba.fastjson.JSON;

/**
 * @author ：jyk
 * @date ：Created in 2020/4/6 21:06
 * @description：
 */
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;

    public ApiResult setCode(ResultCodeEnum resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
