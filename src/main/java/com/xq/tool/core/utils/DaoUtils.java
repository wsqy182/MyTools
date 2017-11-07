package com.xq.tool.core.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
public class DaoUtils {

  private static SessionFactory sf = null;


  public static void init() {
    System.out.println("正在初始化DaoUtils");
    sf = (SessionFactory) ApplicationContextHolder.getBean("sessionFactory");
  }

  public static SessionFactory getSf() {
    return sf;
  }

  public static Session getSession() {
    return sf.getCurrentSession();
  }

  /**
   * @param sql
   * @return
   */
  public static List<Map<String, Object>> exeSqlQuery(String sql) {
    return getSession().createNativeQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  /**
   * @param hql
   * @return
   */
  public static List<Map<String, Object>> exeHqlQuery(String hql) {
    return getSession().createNativeQuery(hql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  /**
   * @param sql
   * @return
   */
  public static List<Map<String, Object>> exeSqlQuery(String sql, Map<String, Object> param) {
    NativeQuery query = getSession().createNativeQuery(sql);
    if (param != null) {
      for (String key : param.keySet()) {
        query.setParameter(key, param.get(key));
      }
    }
    return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  /**
   * @param hql
   * @return
   */
  public static List<Map<String, Object>> exeHqlQuery(String hql, Map<String, Object> param) {
    Query query = getSession().createQuery(hql);
    if (param != null) {
      for (String key : param.keySet()) {
        query.setParameter(key, param.get(key));
      }
    }
    return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
  }

  /**
   * @param sql
   * @return
   */
  public static List<Map<String, Object>> exeSqlQuery(String sql, Map<String, Object> param, int page, int rows) {
    NativeQuery query = getSession().createNativeQuery(sql);
    if (param != null) {
      for (String key : param.keySet()) {
        query.setParameter(key, param.get(key));
      }
    }
    return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }

  /**
   * @param hql
   * @return
   */
  public static List<Map<String, Object>> exeHqlQuery(String hql, Map<String, Object> param, int page, int rows) {
    Query query = getSession().createQuery(hql);
    if (param != null) {
      for (String key : param.keySet()) {
        query.setParameter(key, param.get(key));
      }
    }
    return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
  }
}
