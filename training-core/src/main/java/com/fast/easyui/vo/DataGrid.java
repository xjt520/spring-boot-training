package com.fast.easyui.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by xjt520 on 16/6/28.
 */
@Data
public class DataGrid extends UIData{
    private long total;
    private List rows;
}
