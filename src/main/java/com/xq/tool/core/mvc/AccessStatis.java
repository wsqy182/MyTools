package com.xq.tool.core.mvc;

import com.xq.tool.core.utils.Constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 访问统计工具类
 */
public class AccessStatis {
  /**
   * 非法请求:
   */
  public static final int By_URLError = 1;
  /**
   * 参数不全
   */
  public static final int By_ParamNotFull = 2;
  /**
   * 有token 无Uid 的情况下,该请求非法,非法请求
   */
  public static final int By_HaveTokenNoUid = 3;
  /**
   * token 不符合这算法
   */
  public static final int By_TokenNotFromMySite = 4;
  /**
   * 用户对应的token不存在
   */
  public static final int By_UserNotHaveToken = 5;
  /**
   * token不对等
   */
  public static final int By_TokenNotEquals = 6;

  /**
   * 用户不存在
   */
  public static final int By_UserNotExits = 7;

  public static int accessAccounts = 0;
  private static int definedCount = 0;
  private static Map<Integer, Cause> causeMap;

  private AccessStatis() {

  }

  /**
   * 打印相关的拦截信息
   */
  public static void print() {
    System.out.println("访问次数:" + accessAccounts + ".\t过滤次数:" + definedCount + ".\t服务次数:" + (accessAccounts - definedCount) + ".");
    for (Integer a : causeMap.keySet()) {
      System.out.println("defined cause:" + causeMap.get(a).toString());
    }
  }

  /**
   * 给定错误的信息ID,并返回错误的响应的错误对象
   *
   * @param id
   * @return
   */
  public static Cause defined(final int id) {
    definedCount++;
    causeMap.get(id).count++;
    if (Constant.isDev) {
      System.out.println(causeMap.get(id).msg);
    }
    return causeMap.get(id);
  }

  public static void init() {
    causeMap = new HashMap<Integer, Cause>();
    // ------------------
    Cause a = new Cause();
    a.id = 1;
    a.msg = "URL错误";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 2;
    a.msg = "接口参数不全";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 3;
    a.msg = "有token 无Uid 的情况下,该请求非法,非法请求";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 4;
    a.msg = "token 验证失败";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 5;
    a.msg = "用户对应的token不存在,直接过滤此类请求";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 6;
    a.msg = "token不对等,直接过滤此类请求";
    a.count = 0;
    causeMap.put(a.id, a);
    //--------------------
    a = new Cause();
    a.id = 7;
    a.msg = "当前用户不存在!";
    a.count = 0;
    causeMap.put(a.id, a);
  }
}
