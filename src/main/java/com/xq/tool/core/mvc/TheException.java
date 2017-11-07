package com.xq.tool.core.mvc;

public class TheException extends Exception {
  private static final String default_msg = "";
  private String msg;

  public TheException(String paramMsg) {
    if (paramMsg == null) {
      msg = default_msg;
    } else {
      msg = paramMsg;
    }
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
