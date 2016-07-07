package com.fast.condition;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xjt520 on 16/6/26.
 */
public interface ConditionMapper<T> {
    @Options(keyProperty = "id")
    @SelectProvider(type = ConditionProvider.class, method = "dynamicSQL")
    List<T> findByCondition(@Param(value = "conditions")List<Condition> conditions);
}
