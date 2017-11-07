package com.xq.tool.mvc.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bug", schema = "tool", catalog = "")
public class BugVO {
  private String id;
  private String url;
  private String cause;
  private Date createDate;
  private Date updateDate;
  private Integer count;
  private Integer isFix;
  private Date fixTime;

  @Id
  @Column(name = "id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Basic
  @Column(name = "url")
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Basic
  @Column(name = "cause")
  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
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
  @Column(name = "updateDate")
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  @Basic
  @Column(name = "count")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  @Basic
  @Column(name = "isFix")
  public Integer getIsFix() {
    return isFix;
  }

  public void setIsFix(Integer isFix) {
    this.isFix = isFix;
  }

  @Basic
  @Column(name = "fixTime")
  public Date getFixTime() {
    return fixTime;
  }

  public void setFixTime(Date fixTime) {
    this.fixTime = fixTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BugVO bugVO = (BugVO) o;

    if (id != null ? !id.equals(bugVO.id) : bugVO.id != null) return false;
    if (url != null ? !url.equals(bugVO.url) : bugVO.url != null) return false;
    if (cause != null ? !cause.equals(bugVO.cause) : bugVO.cause != null) return false;
    if (createDate != null ? !createDate.equals(bugVO.createDate) : bugVO.createDate != null) return false;
    if (updateDate != null ? !updateDate.equals(bugVO.updateDate) : bugVO.updateDate != null) return false;
    if (count != null ? !count.equals(bugVO.count) : bugVO.count != null) return false;
    if (isFix != null ? !isFix.equals(bugVO.isFix) : bugVO.isFix != null) return false;
    if (fixTime != null ? !fixTime.equals(bugVO.fixTime) : bugVO.fixTime != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (url != null ? url.hashCode() : 0);
    result = 31 * result + (cause != null ? cause.hashCode() : 0);
    result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
    result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
    result = 31 * result + (count != null ? count.hashCode() : 0);
    result = 31 * result + (isFix != null ? isFix.hashCode() : 0);
    result = 31 * result + (fixTime != null ? fixTime.hashCode() : 0);
    return result;
  }
}
