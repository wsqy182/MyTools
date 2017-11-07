package com.xq.tool.core.mvc;

/**
 * 被过滤的原因.
 * <p>
 * 辅助类
 */
public class Cause {
  public int id;
  public String msg = "";
  public int count = 0;

  @Override
  public String toString() {
    return "\tid:" + id + "\t原因:" + msg + "\t次数:" + count;
  }
}