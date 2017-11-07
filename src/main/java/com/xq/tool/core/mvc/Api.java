package com.xq.tool.core.mvc;

import org.springframework.util.StringUtils;

public class Api {
  public String servletName;
  public String methodName;
  public String serviceName;

  public Api(String servletName, String methodName, String serviceName) {
    this.servletName = servletName;
    this.methodName = methodName;
    this.serviceName = serviceName;
  }

  public Api() {
  }

  @Override
  public String toString() {
    return "Api{" +
        "servletName='" + servletName + '\'' +
        ", methodName='" + methodName + '\'' +
        ", serviceName='" + serviceName + '\'' +
        '}';
  }

  /**
   * API接口是否为空
   */
  public boolean isEmpty() {
    if (StringUtils.isEmpty(this.servletName) && StringUtils.isEmpty(this.methodName) && StringUtils.isEmpty(this.serviceName)) {
      return true;
    }
    return false;
  }

  public String getServletName() {
    return servletName;
  }

  public void setServletName(String servletName) {
    this.servletName = servletName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }
}