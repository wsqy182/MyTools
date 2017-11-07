package com.xq.tool.core.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MACHENIKE on 2017/7/11.
 */
public interface IBaseDao<T> {
    /**
     * 根据ID获取实体
     * @param entityClass
     * @param id
     * @return
     */
    T get(Class<T> entityClass, Serializable id);

    /**
     * 保存实体
     * @return
     */
    Serializable save(T entity);

    /**
     * 更新实体
     * @param entity
     */
    void saveOrUpdate(T entity);

    /**
     * 删除实体
     * @param entity
     */
    void delete(T entity);
    /**
     * 删除实体
     * @param
     */
    void delete(Class<T> entityClazz, Serializable id);

    /**
     * 查询全部
     * @param entity
     * @param id
     * @return
     */
    List<T> findAll(Class<T> entity, Serializable id);

    /**
     * 获取实体总数
     * @param enetityClazz
     * @return
     */
    long findCount(Class<T> enetityClazz);
}
