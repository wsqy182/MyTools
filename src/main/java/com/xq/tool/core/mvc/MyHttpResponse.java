package com.xq.tool.core.mvc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xq.tool.core.utils.Constant;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * HttpResponse 包装类
 * <p>
 * 对原有的HttpResponse 进行了一些扩展
 */
public class MyHttpResponse extends HttpServletResponseWrapper {
  /**
   * 被过滤过滤器的非法请求,响应码为500.
   * <p>
   * 当某个请求被过滤器过滤时,理论上响应的状态应该是500
   * <p>
   * 但是为了前后端联调方便.
   * <p>
   * 该响应码仅在正式环境上作为默认的响应码使用
   */
  private static final int res_status_500 = 500;
  /**
   * 被过滤过滤器的非法请求,响应码为500
   * <p>
   * 当某个请求被过滤器过滤时,理论上响应的状态应该是500
   * <p>
   * 但是为了前后端联调方便.
   * <p>
   * 该响应码仅在测试环境上作为指定的响应码使用
   *
   * @deprecated 仅在开发模式下此属性才用的到
   */
  private static final int res_status_405 = 405;
  /**
   * 过期的访问权限
   */
  private static final int res_status_401 = 401;
  /**
   * 访问的接口错误
   */
  private static final int res_status_404 = 404;
  /**
   * 参数校验错误.
   * <p>
   * 意味者调用接口的人给定的参数不符合协定.
   */
  private static final int res_status_301 = 301;
  /**
   * 程序代码错误.
   * <p>
   * 意味者代码编写错误.
   * <p>
   * 通常由于后台SQL编写错误,抛出的运行时异常,都使用这个状态码返回
   */
  private static final int res_status_302 = 302;
  /**
   * 成功返回200
   */
  private static final int res_status_200 = 200;
  /**
   * 默认常量_业务逻辑成功
   */
  private static final int code_success = 200;
  /**
   * 默认_业务逻辑失败
   */
  private static final int code_failed = 500;
  /**
   * 业务成功_默认响应消息
   */
  private static final String defaultMsg_success = "响应成功!";
  /**
   * 业务失败_默认响应消息
   */
  private static final String defaultMsg_failed = "响应失败!";
  private Api api = null;
  /**
   * 状态码为200的时候返回的JSONObject
   */
  private JSONObject result;
  /**
   * 该请求被过滤的原因.
   * <p>
   * 默认为null
   */
  private Cause filter_Cause = null;
  /**
   * 该请求是否被过滤.
   * <p>
   * 默认为假
   */
  private boolean isFiltered = false;
  private String requestUrl = "";

  /**
   * 构造函数
   *
   * @param response
   */
  public MyHttpResponse(HttpServletResponse response) {
    super(response);
    JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
    this.setContentType("text/javascript;charset=utf-8");
    this.setCharacterEncoding("utf-8");
    result = new JSONObject();
  }

  /**
   * 获取 API
   *
   * @return
   */
  public Api getApi() {
    return api;
  }

  public void setApi(Api api) {
    this.api = api;
  }

  /**
   * 获取当前请求被过滤的原因
   *
   * @return
   * @deprecated 该方法仅在开发模式下有效
   */
  private Cause getFilter_Cause() {
    if (Constant.isDev) {
      return filter_Cause;
    }
    return null;
  }

  /**
   * 设置当前请求被过滤的原因
   *
   * @param filter_Cause
   */
  public void setFilter_Cause(Cause filter_Cause) {
    if (Constant.isDev) {
      this.isFiltered = true;
      this.filter_Cause = filter_Cause;
    }
  }

  /**
   * 当前请求是否已经被过滤器过滤
   *
   * @return 当前请求是否已经被过滤过滤
   * @throws TheException
   * @deprecated 仅在开发模式下有效
   */
  public boolean isFiltered() throws TheException {
    if (Constant.isDev) {
      return isFiltered;
    }
    return false;
  }

  /**
   * 设置当前被过滤的标记
   *
   * @param filtered
   * @deprecated 仅在
   */
  public void setFiltered(boolean filtered) {
    if (Constant.isDev) {
      isFiltered = filtered;
    }
  }

  /**
   * 设置响应头中的Token
   *
   * @param token
   */
  public void setToken(String token) {
    this.setHeader("token", token);
  }

  /**
   * 响应成功
   *
   * @param data [Object] 需要返回的数据,可以是任意对象
   * @return
   */
  public MyHttpResponse success(Object data) throws IOException {
    return response(code_success, data);
  }

  /**
   * 响应成功,业务逻辑失败
   *
   * @param msg [String] 需要返回的数据,可以是任意对象
   * @return
   */
  public MyHttpResponse failed(String msg) throws IOException {
    return response(code_failed, msg);
  }

  /**
   * 响应
   *
   * @param code
   * @param data
   * @return
   * @throws IOException
   */
  public MyHttpResponse response(int code, Object data) throws IOException {
    this.setStatus(code_success);
    result.put("code", code);
    if (code == code_failed) {
      if (StringUtils.isEmpty(data)) {
        data = defaultMsg_failed;
      }
      result.put("msg", data);
    } else {
      if (data == null) {
        data = defaultMsg_success;
      }
      result.put("data", data);
    }
    this.getWriter().append(result.toString());
    return this;
  }


  /**
   * 请求失败
   *
   * @param msg 失败的原因,方便接口调试
   * @return 返回响应字符串
   */
  public MyHttpResponse failed_301(String msg) throws IOException {
    this.setStatus(res_status_301);
    result.put("code", code_failed);
    if (msg == null) {
      msg = defaultMsg_failed;
    }
    result.put("msg", msg);
    this.getWriter().append(result.toString());
    return this;
  }

  /**
   * 程序代码错误.
   * <p>
   * 当某个接口内部出现了错误,使用这个接口返回对应的错误信息.
   * <p>
   * 前端在接到服务器端响应的时候,会根据响应码进行过滤.
   *
   * @return
   * @throws IOException
   */
  public MyHttpResponse failed_302(String errorMsg) throws IOException {
    this.setStatus(res_status_302);
    result.put("code", code_failed);
    if (Constant.isDev) {
      // 开发环境,有接口内部错误,不统计Bug,直接在前端打印
      result.put("msg", errorMsg);
    } else {
      // 正式环境,有接口内部错误,前端不打印接口错误信息,直接统计到Bug系统当中
      result.put("msg", "接口有错误,请联系管理员解决问题");
      // 收集Bug
      TheCache.putBug(this.getApi(), errorMsg, this.getRequestUrl());
    }
    this.getWriter().append(result.toString());
    return this;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  /**
   * 过期的访问令牌
   *
   * @return
   * @throws IOException
   */
  public MyHttpResponse failed_401() throws IOException {
    this.setStatus(res_status_401);
    result.put("code", code_failed);
    result.put("msg", "请重新登录!");
    this.getWriter().append(result.toString());
    return this;
  }

  /**
   * 访问失败.
   * <p>
   * cause:接口不存在
   *
   * @return
   * @throws IOException
   */
  public MyHttpResponse failed_404() throws IOException {
    this.setStatus(res_status_404);
    result.put("code", code_failed);
    result.put("msg", "你访问的接口不存在!");
    this.getWriter().append(result.toString());
    return this;
  }

  /**
   * 响应失败_405
   * <p>
   * 该方法仅在开发模式有效.
   * <p>
   * 用于前后端接口联调时可以看到某个请求被过滤的原因
   * <p>
   * 设计该方法的原因是考虑到,被请求器过滤的请求将无法转发到Servlet层.将直接被过滤掉.为了知道被过滤的原因.设计此接口,方便前后端联调.
   *
   * @return
   * @throws IOException,TheException
   * @deprecated 仅在开发模式下有效
   */
  public MyHttpResponse failed_405() throws IOException {
    if (Constant.isDev) {// 是否为开发模式
      this.setStatus(res_status_405);
      this.getWriter().append(this.getFilter_Cause().msg);
    }
    return this;
  }
}
