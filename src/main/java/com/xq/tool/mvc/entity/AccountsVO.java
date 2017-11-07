package com.xq.tool.mvc.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "accounts", schema = "tool", catalog = "")
public class AccountsVO {
  private int userId;
  private String username;
  private String nickname;
  private String password;
  private String email;
  private String telphone;
  private String wxAccounts;
  private Timestamp registerTime;
  private String registerIp;
  private String registerMachine;
  private Timestamp lastLoginTime;
  private String lastLoginIp;
  private String lastLoginMachine;
  private Integer loginCount;
  private Integer onlineTime;

  @Id
  @Column(name = "userId")
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "username")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Basic
  @Column(name = "nickname")
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Basic
  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "telphone")
  public String getTelphone() {
    return telphone;
  }

  public void setTelphone(String telphone) {
    this.telphone = telphone;
  }

  @Basic
  @Column(name = "wxAccounts")
  public String getWxAccounts() {
    return wxAccounts;
  }

  public void setWxAccounts(String wxAccounts) {
    this.wxAccounts = wxAccounts;
  }

  @Basic
  @Column(name = "registerTime")
  public Timestamp getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(Timestamp registerTime) {
    this.registerTime = registerTime;
  }

  @Basic
  @Column(name = "registerIp")
  public String getRegisterIp() {
    return registerIp;
  }

  public void setRegisterIp(String registerIp) {
    this.registerIp = registerIp;
  }

  @Basic
  @Column(name = "registerMachine")
  public String getRegisterMachine() {
    return registerMachine;
  }

  public void setRegisterMachine(String registerMachine) {
    this.registerMachine = registerMachine;
  }

  @Basic
  @Column(name = "lastLoginTime")
  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  @Basic
  @Column(name = "lastLoginIp")
  public String getLastLoginIp() {
    return lastLoginIp;
  }

  public void setLastLoginIp(String lastLoginIp) {
    this.lastLoginIp = lastLoginIp;
  }

  @Basic
  @Column(name = "lastLoginMachine")
  public String getLastLoginMachine() {
    return lastLoginMachine;
  }

  public void setLastLoginMachine(String lastLoginMachine) {
    this.lastLoginMachine = lastLoginMachine;
  }

  @Basic
  @Column(name = "loginCount")
  public Integer getLoginCount() {
    return loginCount;
  }

  public void setLoginCount(Integer loginCount) {
    this.loginCount = loginCount;
  }

  @Basic
  @Column(name = "onlineTime")
  public Integer getOnlineTime() {
    return onlineTime;
  }

  public void setOnlineTime(Integer onlineTime) {
    this.onlineTime = onlineTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AccountsVO basicVO = (AccountsVO) o;

    if (userId != basicVO.userId) return false;
    if (username != basicVO.username) return false;
    if (nickname != null ? !nickname.equals(basicVO.nickname) : basicVO.nickname != null) return false;
    if (password != null ? !password.equals(basicVO.password) : basicVO.password != null) return false;
    if (email != null ? !email.equals(basicVO.email) : basicVO.email != null) return false;
    if (telphone != null ? !telphone.equals(basicVO.telphone) : basicVO.telphone != null) return false;
    if (wxAccounts != null ? !wxAccounts.equals(basicVO.wxAccounts) : basicVO.wxAccounts != null) return false;
    if (registerTime != null ? !registerTime.equals(basicVO.registerTime) : basicVO.registerTime != null) return false;
    if (registerIp != null ? !registerIp.equals(basicVO.registerIp) : basicVO.registerIp != null) return false;
    if (registerMachine != null ? !registerMachine.equals(basicVO.registerMachine) : basicVO.registerMachine != null)
      return false;
    if (lastLoginTime != null ? !lastLoginTime.equals(basicVO.lastLoginTime) : basicVO.lastLoginTime != null)
      return false;
    if (lastLoginIp != null ? !lastLoginIp.equals(basicVO.lastLoginIp) : basicVO.lastLoginIp != null) return false;
    if (lastLoginMachine != null ? !lastLoginMachine.equals(basicVO.lastLoginMachine) : basicVO.lastLoginMachine != null)
      return false;
    if (loginCount != null ? !loginCount.equals(basicVO.loginCount) : basicVO.loginCount != null) return false;
    if (onlineTime != null ? !onlineTime.equals(basicVO.onlineTime) : basicVO.onlineTime != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (telphone != null ? telphone.hashCode() : 0);
    result = 31 * result + (wxAccounts != null ? wxAccounts.hashCode() : 0);
    result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
    result = 31 * result + (registerIp != null ? registerIp.hashCode() : 0);
    result = 31 * result + (registerMachine != null ? registerMachine.hashCode() : 0);
    result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
    result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
    result = 31 * result + (lastLoginMachine != null ? lastLoginMachine.hashCode() : 0);
    result = 31 * result + (loginCount != null ? loginCount.hashCode() : 0);
    result = 31 * result + (onlineTime != null ? onlineTime.hashCode() : 0);
    return result;
  }
  @Transient
  public boolean isRigesUser() {
    return true;
  }
}
