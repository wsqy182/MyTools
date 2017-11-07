package com.xq.tool.mvc.form;


import com.xq.tool.core.annotation.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * login表单
 */
@Component
@Scope("prototype")
// 表单对象
public class LoginForm extends BaseForm {

  @NotNull(filedName = "用户名")
  public String accounts;

  @NotNull(filedName = "密码")
  public String password;

  public String getAccounts() {
    return accounts;
  }

  public void setAccounts(String accounts) {
    this.accounts = accounts;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "LoginForm{" +
        "accounts='" + accounts + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
