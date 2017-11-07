package com.xq.tool.core.mvc;

import com.xq.tool.core.utils.Constant;
import com.xq.tool.core.utils.DaoUtils;
import com.xq.tool.core.utils.OtherUtils;
import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.entity.BugVO;
import com.xq.tool.mvc.entity.TokenVO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义的缓存
 */
public class TheCache {
  private static Map<Integer, TokenVO> tokenCache = null;
  private static Map<Integer, AccountsVO> pubUserCache = null;
  private static Map<String, BugVO> bugMapCache = null;


  /**
   * 查看BugMap
   *
   * @return
   */
  public static Map<String, BugVO> getBugMap() {
    return bugMapCache;
  }

  /**
   * 查看TokenMap
   * <p>
   * 仅在dev模式开启下返回结果
   */
  public static Map<Integer, TokenVO> getTokenMap() {
    if (Constant.isDev) {
      return tokenCache;
    }
    return null;
  }

  /**
   * 查看UserMap
   * <p>
   * 仅在dev模式开启下返回结果
   */
  public static Map<Integer, AccountsVO> getUserMap() {
    if (Constant.isDev) {
      return pubUserCache;
    }
    return null;
  }


  /**
   * 销毁方法.
   * <p>
   * 持久化各类缓存数据
   */
  public static void destroy() {
    SessionFactory sf = DaoUtils.getSf();
    Session session = sf.openSession();
    // 1.持久化Token
    Transaction tx = session.beginTransaction();
    try {
      for (Integer uid : tokenCache.keySet()) {
        session.saveOrUpdate(tokenCache.get(uid));
      }
      tx.commit();
      System.out.println("持久化token:" + tokenCache.size() + "条 success!");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("持久化token:faild!");
      tx.rollback();
    }
    // 2.持久化Bug
    Transaction tx2 = session.beginTransaction();
    try {
      for (String id : bugMapCache.keySet()) {
        session.saveOrUpdate(bugMapCache.get(id));
      }
      tx2.commit();
      System.out.println("已收集Bug:" + bugMapCache.size() + "条");
    } catch (Exception e) {
      System.out.println("持久化Bug: faild!");
      tx2.rollback();
    }
    session.close();
  }

  /**
   * 收集一个Bug
   *
   * @param errorMsg 产生错误的信息
   * @param api      产生错误的接口
   * @param url      产生错误的URL
   */
  public static void putBug(Api api, String errorMsg, String url) {
    String id = api.servletName + "-" + api.serviceName + "-" + api.methodName + ":" + OtherUtils.getMd5OrUUID(errorMsg);
    BugVO bug = TheCache.getBugVO(id);
    if (bug == null) {// 新的Bug
      bug.setId(id);
      bug.setCreateDate(new Date());
    }
    bug.setCause(errorMsg);
    bug.setUrl(url);
    bug.setCount(bug.getCount() + 1);
    bug.setIsFix(0);
    bug.setUpdateDate(new Date());
    bug.setFixTime(null);
    bugMapCache.put(id, bug);
  }

  /**
   * 缓存已经登录的用户.
   *
   * @param accountsVO
   */
  public static void putPubUser(AccountsVO accountsVO) {
    pubUserCache.put(accountsVO.getUserId(), accountsVO);
  }

  /**
   * 缓存已经颁发的Token
   *
   * @param token
   */
  public static void putToken(TokenVO token) {
    tokenCache.put(token.getUserId(), token);
  }

  /**
   * 获取指定的token
   *
   * @param userId
   * @return
   */
  public static TokenVO getTokenCache(int userId) {
    return tokenCache.get(userId);
  }

  /**
   * 获取指定的用户
   *
   * @param userId
   * @return
   */
  public static AccountsVO getPubUserCache(int userId) {
    return pubUserCache.get(userId);
  }


  /**
   * 获取指定的Bug
   *
   * @param id
   * @return
   */
  public static BugVO getBugVO(String id) {
    return bugMapCache.get(id);
  }

  /**
   * 调用初始化,
   * <p>
   * 从数据库中加载所有的用户清单和用户Token.
   */
  public static void init() {
    initBugCache();
    initTokenCache();
    initUserCache();
  }

  /**
   * 初始化用户缓存
   */
  private static void initTokenCache() {
    //1.准备线程池
    tokenCache = new HashMap<Integer, TokenVO>();
    //2.读取所有的Token数据
    SessionFactory sf = DaoUtils.getSf();
    Session session = sf.openSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("from TokenVO");
    List<TokenVO> tokenList = query.list();
    tx.commit();
    session.close();
    //3.缓存所有的token数据
    if (tokenList != null) {
      for (TokenVO cacheVO : tokenList) {
        tokenCache.put(cacheVO.getUserId(), cacheVO);
      }
    }
  }

  /**
   * 初始化用户缓存
   */
  private static void initUserCache() {
    //1.准备缓存
    pubUserCache = new HashMap<Integer, AccountsVO>();
    //2.读取所有的用户数据
    SessionFactory sf = DaoUtils.getSf();
    Session session = sf.openSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("from AccountsVO ");
    List<AccountsVO> userList = query.list();
    tx.commit();
    // 2.1先关闭会话,让userList脱离Hibernate Session 的托管,成为游离态.否则接下来的隐藏密码的动作,将直接持久化到Session当中
    session.close();
    //3.缓存所有的用户数据
    if (userList != null) {
      for (AccountsVO user : userList) {
        // 3.1 隐藏用户密码.
        user.setPassword("*");
        pubUserCache.put(user.getUserId(), user);
      }
    }
  }

  /**
   * 初始化Bug缓存
   */
  private static void initBugCache() {
    //1.准备缓存
    bugMapCache = new HashMap<String, BugVO>();
    //2.读取所有的用户数据
    SessionFactory sf = DaoUtils.getSf();
    Session session = sf.openSession();
    Transaction tx = session.beginTransaction();
    Query query = session.createQuery("from BugVO ");
    List<BugVO> bugList = query.list();
    tx.commit();
    session.close();
    //3.缓存所有的用户数据
    if (bugList != null) {
      for (BugVO bug : bugList) {
        bugMapCache.put(bug.getId(), bug);
      }
    }
  }
}
