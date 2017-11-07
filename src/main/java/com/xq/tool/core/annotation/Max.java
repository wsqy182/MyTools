package com.xq.tool.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查最大值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Max {
  /**
   * 最小值
   *
   * @return
   */
  int maxValue() default 0;

  /**
   * 字段名
   *
   * @return
   */
  String fieldName() default "";
}
