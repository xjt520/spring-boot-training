package com.fast.base;

import com.fast.condition.Condition;
import com.fast.condition.PageCondition;
import com.fast.conts.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by xjt520 on 16/6/26.
 * Service 基类
 */
public abstract class BaseBiz<T extends BaseIncrementIdModel, K extends BaseMapper<T>> {

    /**
     * dao原型属性
     */
    protected K baseDao;

    /**
     * 根据K泛型自动装载BaseDao
     */
    @Autowired
    public final void setBaseDao(K baseDao) {
        this.baseDao = baseDao;
    }

    /***************************读操作***************************/

    public T findById(Long id){
        // -1 代表 NULL
        if (id == null || id.toString().equals("-1"))
            return null;
        return baseDao.selectByPrimaryKey(id);
    }

    public T findFirst(List<Condition> conditions){
        List<T> list = find(conditions);
        return (list!=null && list.size()>0) ? list.get(0) : null;
    }

    /**
     * 条件查询
     * 传入null 默认查询所有
     * @param conditions
     * @return
     */
    public List<T> find(List<Condition> conditions){
        return baseDao.findByCondition(conditions);
    }

    /**
     * 分页条件查询
     * @param conditions
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> pagination(List<Condition> conditions,Integer page,Integer rows){
        page = page == null ? R.P.PAGE : page;
        rows = rows == null ? R.P.ROWS : rows;
        PageHelper.startPage(page, rows, "id");
        List<T> list = baseDao.findByCondition(conditions);
        PageInfo<T> pageInfo = new PageInfo<T>(list,R.P.NAVIGATE);
        return pageInfo;
    }

    /**
     * 分页条件查询
     * @param pageCondition
     * @return
     */
    public PageInfo<T> pagination(PageCondition pageCondition){
        return pagination(pageCondition.getConditions().getItems(),pageCondition.getPage(),pageCondition.getRows());
    }

    /***************************写操作***************************/

    public void save(T entity) {
        if (entity.getId() == null) {
            baseDao.insert(entity);
        } else {
            baseDao.updateByPrimaryKey(entity);
        }
    }

    public void deleteById(Object id){
        baseDao.deleteByPrimaryKey(id);
    }

}
