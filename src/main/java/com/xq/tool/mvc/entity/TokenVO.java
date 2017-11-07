package com.xq.tool.mvc.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token", schema = "tool", catalog = "")
public class TokenVO {
  private int userId;
  private String token;
  private Date createDate;
  private Integer validTime;

  @Id
  @Column(name = "userId")
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Basic
  @Column(name = "token")
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Basic
  @Column(name = "createDate")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Basic
  @Column(name = "validTime")
  public Integer getValidTime() {
    return validTime;
  }

  public void setValidTime(Integer validTime) {
    this.validTime = validTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TokenVO tokenVO = (TokenVO) o;

    if (userId != tokenVO.userId) return false;
    if (token != null ? !token.equals(tokenVO.token) : tokenVO.token != null) return false;
    if (createDate != null ? !createDate.equals(tokenVO.createDate) : tokenVO.createDate != null) return false;
    if (validTime != null ? !validTime.equals(tokenVO.validTime) : tokenVO.validTime != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId;
    result = 31 * result + (token != null ? token.hashCode() : 0);
    result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
    result = 31 * result + (validTime != null ? validTime.hashCode() : 0);
    return result;
  }
}
