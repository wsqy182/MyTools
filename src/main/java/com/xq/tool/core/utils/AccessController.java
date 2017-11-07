package com.xq.tool.core.utils;

import com.xq.tool.core.mvc.MyHttpRequest;
import com.xq.tool.core.mvc.MyHttpResponse;
import com.xq.tool.core.mvc.TheException;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 控制访问的总servlet
 * 负责拦截所有访问api的url
 * 该类主要功能负责拦截访问该类的所有请求,并分发处理和权限拦截
 */
public class AccessController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(AccessController.class);
  /**
   * 访问类型_get
   * 备用
   * 用于后期扩展
   */
  private static int ACCESS_TYPE_GET = 0;
  /**
   * 访问类型_post
   * 备用
   * 用于后期扩展
   */
  private static int ACCESS_TYPE_POST = 1;
  private WebApplicationContext context = null;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      requestHandler((MyHttpRequest) request, (MyHttpResponse) response, ACCESS_TYPE_GET);
    } catch (TheException e) {
      e.printStackTrace();
      throw new IOException(e.getCause().getMessage());
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      requestHandler((MyHttpRequest) request, (MyHttpResponse) response, ACCESS_TYPE_POST);
    } catch (TheException e) {
      e.printStackTrace();
      throw new IOException(e.getCause().getMessage());
    }
  }

  @Override
  public void init() throws ServletException {
    logger.info("正在初始化总控制台!");
    ServletContext servletContext = this.getServletContext();
    context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        DaoUtils.init(context);
    super.init();
  }

  /**
   * 请求处理函数
   * 接口格式:// http://ip:port/api(servlet名称)/action(要访问的action类名)/method(要访问的方法)
   *
   * @param request
   * @param response
   * @param accessType 访问类型,备用,用于后期扩展
   * @throws ServletException
   * @throws IOException
   */
  private void requestHandler(MyHttpRequest request, MyHttpResponse response, int accessType)
      throws ServletException, IOException, TheException {
    if (Constant.isDev) {
      if (response.isFiltered()) {// 返回405状态码和相关的过滤原因
        response.failed_405();
        return;
      }
    }
    if (request.isExpireFlag()) {// 访问权限已经过期.返回
      response.failed_401();
      return;
    }
    if (request.isParamError()) {// 接口参数错误,返回
      response.failed_404();
      return;
    }

    // TODO:设计过滤器响应代码


    this.dispatch(request, response);
  }

  /**
   * 分发Action
   *
   * @param req
   * @param res
   * @throws IOException
   */
  public void dispatch(MyHttpRequest req, MyHttpResponse res) throws IOException {
    Object bean = this.context.getBean(req.getApi().getServiceName() + "Action");
    Class clazz = bean.getClass();
    Method method = null;
    try {
      method = clazz.getMethod(req.getApi().getMethodName(), MyHttpRequest.class, MyHttpResponse.class);
      method.invoke(bean, req, res);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
      //"无匹配接口!"
      res.failed_404();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      //"安全权限异常!"
      res.failed_404();
    } catch (InvocationTargetException e) {
      // 方法内部异常.
      // 仅调试期间有用
      e.printStackTrace();
      if (Constant.isDev) {
        res.failed_302(e.getCause().getMessage());
      } else {
        res.failed_302("接口内部异常,请联系管理员接口");
      }
    }
    return;
  }

}
