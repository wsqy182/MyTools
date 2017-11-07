package com.xq.tool.mvc.entity;

/**
 *
 */
public class AnonymousUser extends AccountsVO {

  @Override
  public boolean isRigesUser() {
    return false;
  }
}
