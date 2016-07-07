package com.fast.condition;

/**
 * Created by xjt520 on 16/6/28.
 */
public class PageCondition {

    private ConditionMap conditions;
    private Integer page;
    private Integer rows;

    public ConditionMap getConditions() {
        if (conditions == null)
            conditions = new ConditionMap();
        return conditions;
    }

    public void setConditions(ConditionMap conditions) {
        this.conditions = conditions;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageCondition [page=" + page+", rows="+rows + ", conditions=" + conditions + "]";
    }
}
