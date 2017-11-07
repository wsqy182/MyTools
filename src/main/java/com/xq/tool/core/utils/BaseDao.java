package com.xq.tool.core.utils;

//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by MACHENIKE on 2017/7/11.
 */
@Repository
public class BaseDao<T> implements IBaseDao<T> {

  @Autowired
  private SessionFactory sessionFactory;

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  /**
   * 根据ID获取实体
   *
   * @param entityClass
   * @param id
   * @return
   */
  public T get(Class<T> entityClass, Serializable id) {
    return (T) getSession().get(entityClass, id);
  }

  /**
   * 保存实体
   *
   * @return
   */
  public Serializable save(T entity) {
    return getSession().save(entity);
  }

  /**
   * 更新实体
   *
   * @param entity
   */
  public void saveOrUpdate(T entity) {
    getSession().saveOrUpdate(entity);
  }

  /**
   * 删除实体
   *
   * @param entity
   */
  public void delete(T entity) {
    getSession().delete(entity);
  }

  /**
   * 删除实体
   *
   * @param
   */
  public void delete(Class<T> entityClazz, Serializable id) {
    getSession().createQuery("delete " + entityClazz.getSimpleName()
        + " a where a.id = ?").setParameter(0, id).executeUpdate();
  }

  /**
   * 查询全部
   *
   * @param entity
   * @param id
   * @return
   */
  public List<T> findAll(Class<T> entity, Serializable id) {
    return find("select en from " + entity.getSimpleName() + " en");
  }

  /**
   * 获取实体总数
   *
   * @param enetityClazz
   * @return
   */
  public long findCount(Class<T> enetityClazz) {
    List<?> l = find("select count(*) from " + enetityClazz.getSimpleName());
    if (l != null && l.size() == 1) {
      return (Long) l.get(0);
    }
    return 0;
  }

  /**
   * 根据SQL语句查询实体
   *
   * @param hql
   * @return
   */
  public List<T> find(String hql) {
    return (List<T>) getSession().createQuery(hql).list();
  }

  /**
   * 根据带占位符HQL语句查询实体
   *
   * @param hql
   * @param params
   * @return
   */
  public List<T> find(String hql, Map<String, Object> params) {
    Query query = getSession().createQuery(hql);
    if (params != null) {
      for (String key : params.keySet()) {
        query.setParameter(key, params.get(key));
      }
    }
    return (List<T>) query.list();
  }

  /**
   * 返回一整套的Map,避免从数据库中load所有字段
   *
   * @param sql
   * @param pageNo
   * @param pageSize
   * @param params
   * @return
   */
  public List<Map<String, Object>> findByPageForMap(String sql, int pageNo, int pageSize, Map<String, Object> params) {
    NativeQuery query = getSession().createNativeQuery(sql);
    if (params != null) {
      for (String key : params.keySet()) {
        query.setParameter(key, params.get(key));
      }
    }
    return query
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
        .setFirstResult((pageNo - 1) * pageSize)
        .setMaxResults(pageSize)
        .list();
  }

  public List<T> findByPageUseSql(String sql, int pageNo, int pageSize, Map<String, Object> params) {
    NativeQuery query = getSession().createNativeQuery(sql);
    if (params != null) {
      for (String key : params.keySet()) {
        query.setParameter(key, params.get(key));
      }
    }
    return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
  }


  public List<T> findByPageUseHql(String hql, int pageNo, int pageSize, Map<String, Object> params) {
    Query query = getSession().createQuery(hql);
    if (params != null) {
      for (String key : params.keySet()) {
        query.setParameter(key, params.get(key));
      }
    }
    return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
  }


}
