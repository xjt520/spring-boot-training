package com.fast.vo;

import com.fast.enums.Ret;
import lombok.Data;

/**
 * Created by xjt520 on 16/6/29.
 */
@Data
public class Result {

    private int code;
    private Object data;
    private String msg;

    public Result(Ret ret) {
        this.code = ret.getCode();
        this.msg = ret.getMsg();
    }
}
