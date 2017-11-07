package com.xq.tool.core.mvc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.form.BaseForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by MACHENIKE on 2017/10/18.
 */
public class MyHttpRequest extends HttpServletRequestWrapper {
  private AccountsVO CurrentUser;
  private JSONObject payload;
  private boolean expireFlag;
  private boolean paramError;
  private Api Api;
  private BaseForm form;

  public BaseForm getForm() {
    return form;
  }

  public void setForm(BaseForm form) {
    this.form = form;
  }

  public MyHttpRequest(HttpServletRequest request) {
    super(request);
  }

  public Api getApi() {
    return Api;
  }

  public void setApi(Api api) {
    Api = api;
  }

  public boolean isParamError() {
    return paramError;
  }

  public void setParamError(boolean paramError) {
    this.paramError = paramError;
  }


  public AccountsVO getCurrentUser() {
    return CurrentUser;
  }

  public void setCurrentUser(AccountsVO currentUser) {
    CurrentUser = currentUser;
  }

  public JSON getPayload() {
    return payload;
  }

  public void setPayload(JSONObject payload) {
    this.payload = payload;
  }

  public boolean isExpireFlag() {
    return expireFlag;
  }

  public void setExpireFlag(boolean expireFlag) {
    this.expireFlag = expireFlag;
  }

}
