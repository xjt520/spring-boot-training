package com.fast.base;

import com.fast.condition.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by xjt520 on 16/6/26.
 * Mapper 基类
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>,ConditionMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
