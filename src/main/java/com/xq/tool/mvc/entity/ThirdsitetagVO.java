package com.xq.tool.mvc.entity;

import javax.persistence.*;

@Entity
@Table(name = "thirdsitetag", schema = "tool", catalog = "")
public class ThirdsitetagVO {
  private int id;
  private int recordId;
  private String tagName;

  @Id
  @Column(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Basic
  @Column(name = "recordId")
  public int getRecordId() {
    return recordId;
  }

  public void setRecordId(int recordId) {
    this.recordId = recordId;
  }

  @Basic
  @Column(name = "tagName")
  public String getTagName() {
    return tagName;
  }

  public void setTagName(String tagName) {
    this.tagName = tagName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ThirdsitetagVO that = (ThirdsitetagVO) o;

    if (id != that.id) return false;
    if (recordId != that.recordId) return false;
    if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + recordId;
    result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
    return result;
  }
}
