package com.fast.easyui.util;

import com.fast.easyui.vo.DataGrid;
import com.github.pagehelper.PageInfo;

/**
 * Created by xjt520 on 16/6/28.
 */
public class UIKit {

    public static DataGrid tableData(PageInfo pageInfo){
        DataGrid dataGrid = new DataGrid();
        dataGrid.setRows(pageInfo.getList());
        dataGrid.setTotal(pageInfo.getTotal());
        return dataGrid;
    }

}
