package com.xq.tool.core.mvc;

import com.alibaba.fastjson.JSONObject;
import com.xq.tool.core.utils.Constant;
import com.xq.tool.core.utils.JWTTool;
import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.entity.AnonymousUser;
import com.xq.tool.mvc.entity.TokenVO;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 二级过滤器
 */
public class MyFilter implements Filter {


  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 初始化访问协议
    AccessStatis.init();
  }

  /**
   * 过滤不符合访问协议的请求
   *
   * @param request
   * @param response
   * @param chain
   * @throws IOException
   * @throws ServletException
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    AccessStatis.accessAccounts++;
    MyHttpRequest myReq = new MyHttpRequest((HttpServletRequest) request);
    MyHttpResponse myRes = new MyHttpResponse((HttpServletResponse) response);

    String userId = myReq.getHeader("uid");
    String token = myReq.getHeader("token");

    // 1.解析接口名是否正确
    String url = myReq.getRequestURL().toString();
    url = url.split("\\?")[0];
    String[] urlArr = url.split("/");

    String methodName = null, serviceName = null;
    Cause cause = null;
    if (urlArr.length < 3) {// 非法请求
      cause = AccessStatis.defined(AccessStatis.By_URLError);
      if (Constant.isDev) {
        myRes.setFilter_Cause(cause);
        chain.doFilter(myReq, myRes);
      }
      return;
    } else {
      Api api = new Api();
      api.setServletName(urlArr[urlArr.length - 3]);
      api.setServiceName(urlArr[urlArr.length - 2]);
      api.setMethodName(urlArr[urlArr.length - 1]);
      if (api.isEmpty()) {
        cause = AccessStatis.defined(AccessStatis.By_ParamNotFull);
        if (Constant.isDev) {
          myRes.setFilter_Cause(cause);
          chain.doFilter(myReq, myRes);
        }
        return;
      }
      myReq.setApi(api);
    }
    /**
     * 2.校验token
     * 通过第一层拦截后,校验token 合法性
     */
    if (!StringUtils.isEmpty(token)) {
      if (StringUtils.isEmpty(userId))// 有token 无Uid 的情况下,该请求非法.
      {
        cause = AccessStatis.defined(AccessStatis.By_HaveTokenNoUid);
        if (Constant.isDev) {
          myRes.setFilter_Cause(cause);
          chain.doFilter(myReq, myRes);
        }
        return;
      }
      JSONObject validateResult = JWTTool.validateToken(token);
//      System.out.println(validateResult);
      if (!validateResult.getBoolean("auth")) { // token 验证失败
        cause = AccessStatis.defined(AccessStatis.By_TokenNotFromMySite);
        if (Constant.isDev) {
          myRes.setFilter_Cause(cause);
          chain.doFilter(myReq, myRes);
        }
        return;
      }
      TokenVO cache = TheCache.getTokenCache(Integer.parseInt(userId));
      if (cache == null) {// 用户对应的token 不存在,直接过滤此类请求
        cause = AccessStatis.defined(AccessStatis.By_UserNotHaveToken);
        if (Constant.isDev) {
          myRes.setFilter_Cause(cause);
          chain.doFilter(myReq, myRes);
        }
        return;
      }
      if (!cache.getToken().equals(token)) {// token不对等,直接过滤此类请求
        cause = AccessStatis.defined(AccessStatis.By_TokenNotEquals);
        if (Constant.isDev) {
          myRes.setFilter_Cause(cause);
          chain.doFilter(myReq, myRes);
        }
        return;
      }
      Date currentDate = new Date();
      if (cache.getCreateDate().getTime() + cache.getValidTime() < currentDate.getTime()) {// token 合法,但是已经过期.
        myReq.setExpireFlag(true);
      }
      // token 通过基本算法校验,取出载体,存入数据库.
      myReq.setPayload(validateResult.getJSONObject("payload"));
    }
    // 3.解析用户
    if (StringUtils.isEmpty(userId)) {
      // 用户ID为空,认为其为匿名用户,
      AnonymousUser anonymousUser = new AnonymousUser();
      String nickname = myReq.getSession().getId().substring(0, 6);
      anonymousUser.setNickname("游客" + nickname);
      myReq.setCurrentUser(anonymousUser);
    } else {
      AccountsVO currentUser = null;
      try {
        Integer accountsId = Integer.parseInt(userId);
        currentUser = TheCache.getPubUserCache(accountsId);
        if (currentUser == null) {
          cause = AccessStatis.defined(AccessStatis.By_UserNotExits);
          if (Constant.isDev) {
            myRes.setFilter_Cause(cause);
            chain.doFilter(myReq, myRes);
          }
          return;
        }
        myReq.setCurrentUser(currentUser);
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }
    chain.doFilter(myReq, myRes);
  }

  /**
   * 持久化
   */
  @Override
  public void destroy() {
    AccessStatis.print();
  }
}