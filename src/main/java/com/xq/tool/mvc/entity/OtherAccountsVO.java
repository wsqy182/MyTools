package com.xq.tool.mvc.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "other_accounts", schema = "tool", catalog = "")
public class OtherAccountsVO {
  private int recordId;
  private int userId;
  private String accounts;
  private String password;
  private String bindTelPhone;
  private String bindEmail;
  private String siteName;
  private String siteUrl;
  private String mark;
  private Date createDate;

  @Id
  @Column(name = "recordId")
  public int getRecordId() {
    return recordId;
  }

  public void setRecordId(int recordId) {
    this.recordId = recordId;
  }

  @Basic
  @Column(name = "userId")
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "accounts")
  public String getAccounts() {
    return accounts;
  }

  public void setAccounts(String accounts) {
    this.accounts = accounts;
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
  @Column(name = "bindTelPhone")
  public String getBindTelPhone() {
    return bindTelPhone;
  }

  public void setBindTelPhone(String bindTelPhone) {
    this.bindTelPhone = bindTelPhone;
  }

  @Basic
  @Column(name = "bindEmail")
  public String getBindEmail() {
    return bindEmail;
  }

  public void setBindEmail(String bindEmail) {
    this.bindEmail = bindEmail;
  }

  @Basic
  @Column(name = "siteName")
  public String getSiteName() {
    return siteName;
  }

  public void setSiteName(String siteName) {
    this.siteName = siteName;
  }

  @Basic
  @Column(name = "siteUrl")
  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  @Basic
  @Column(name = "mark")
  public String getMark() {
    return mark;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }

  @Basic
  @Column(name = "createDate")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OtherAccountsVO that = (OtherAccountsVO) o;

    if (recordId != that.recordId) return false;
    if (userId != that.userId) return false;
    if (accounts != null ? !accounts.equals(that.accounts) : that.accounts != null) return false;
    if (password != null ? !password.equals(that.password) : that.password != null) return false;
    if (bindTelPhone != null ? !bindTelPhone.equals(that.bindTelPhone) : that.bindTelPhone != null) return false;
    if (bindEmail != null ? !bindEmail.equals(that.bindEmail) : that.bindEmail != null) return false;
    if (siteName != null ? !siteName.equals(that.siteName) : that.siteName != null) return false;
    if (siteUrl != null ? !siteUrl.equals(that.siteUrl) : that.siteUrl != null) return false;
    if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
    if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = recordId;
    result = 31 * result + userId;
    result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (bindTelPhone != null ? bindTelPhone.hashCode() : 0);
    result = 31 * result + (bindEmail != null ? bindEmail.hashCode() : 0);
    result = 31 * result + (siteName != null ? siteName.hashCode() : 0);
    result = 31 * result + (siteUrl != null ? siteUrl.hashCode() : 0);
    result = 31 * result + (mark != null ? mark.hashCode() : 0);
    result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
    return result;
  }
}
