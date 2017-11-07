package com.xq.tool.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查最小值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Min {
  /**
   * 最小值
   *
   * @return
   */
  int minValue() default 0;

  /**
   * 字段名
   *
   * @return
   */
  String filedName() default "";
}
