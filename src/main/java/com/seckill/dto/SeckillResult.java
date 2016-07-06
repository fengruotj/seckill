package com.seckill.dto;

/**
 * Created by dello on 2016/7/6.
 * 封装json结构，所有的ajax请求返回的类型
 */
public class SeckillResult<T> {
    private boolean success;

    private T data;

    private  String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success,String error) {
        this.error = error;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
