package com.fast.enums;

/**
 * Created by xjt520 on 16/6/29.
 */
public enum Ret {

    SUCCESS(2000,"操作成功"),

    ERROR(4000,"操作失败"),
    ERROR_FILE_NULL(4001,"文件为空"),
    ERROR_NO_PERMISSION(4002,"没有权限"),

    EXCEPTION(5000,"服务器繁忙,请稍后再试"),
    EXCEPTION_FILE_SIZE(5001,"文件太大");

    private int code;
    private String msg;

    Ret(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
