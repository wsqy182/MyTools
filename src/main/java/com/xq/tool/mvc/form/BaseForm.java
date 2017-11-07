package com.xq.tool.mvc.form;

import com.alibaba.fastjson.JSON;
import com.xq.tool.core.annotation.Max;
import com.xq.tool.core.annotation.Min;
import com.xq.tool.core.annotation.NotNull;
import com.xq.tool.core.annotation.Range;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 表单对象,对于Client的每一个表单,都将有与之对应的表单对象.
 */
@Component
public abstract class BaseForm implements Serializable {
  private BaseForm instance;
  private String m_err_msg;
  private Map<String, Object> prop;
  private String m_name;
  private Class m_class;

  public BaseForm() {

  }


  /**
   * 获取错误信息
   *
   * @return
   */
  public String getM_err_msg() {
    return m_err_msg;
  }

  /**
   * 验证当前表单
   *
   * @return
   */
  public boolean Validate() {
    if (this.instance == null) {
      return false;
    } else {
      Class t = this.instance.getClass();
      Field[] fields = t.getFields();
      try {
        for (int i = 0; i < fields.length; i++) {
          Field f = fields[i];
          if (f.isAnnotationPresent(NotNull.class)) {
            NotNull annotation = f.getAnnotation(NotNull.class);
            String filedName = annotation.filedName();
            if (isEmpty(f.getName(), f.get(instance))) {
              this.m_err_msg = filedName + "不能为空!";
              return false;
            }
          }
          if (f.isAnnotationPresent(Min.class)) {
            Min min = f.getAnnotation(Min.class);
            int minValue = min.minValue();
            String filedName = min.filedName();
            int fieldValue = (int) f.get(instance);
            if (fieldValue < minValue) {
              this.m_err_msg = filedName + "不能小于" + minValue;
              return false;
            }
          }

          if (f.isAnnotationPresent(Max.class)) {
            Max max = f.getAnnotation(Max.class);
            int maxValue = max.maxValue();
            String filedName = max.fieldName();
            int fieldValue = (int) f.get(instance);
            if (fieldValue < maxValue) {
              this.m_err_msg = filedName + "不能大于" + maxValue;
              return false;
            }
          }

          if (f.isAnnotationPresent(Range.class)) {
            Range range = f.getAnnotation(Range.class);
            int left = range.left();
            int right = range.right();
            String filedName = range.fieldName();
            int fieldValue = (int) f.get(instance);
            if (left > fieldValue || fieldValue > right) {
              this.m_err_msg = filedName + "应该在取值范围(" + left + "," + right + ")内";
              return false;
            }
          }

        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
    return true;
  }


  /**
   * 初始化
   *
   * @param instance 自己的实例
   * @param param    request当前的请求参数列表
   * @return
   */
  public boolean init(BaseForm instance, Map<String, Object> param) {
    boolean result = false;
    try {
      //复制表单属性到实例当中
      BeanUtils.populate(instance,param);
      this.instance = (BaseForm) instance;
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      instance = null;
    }
    return false;
  }

  /**
   * 验证方法
   *
   * @param fieldName
   * @param value
   * @return
   */
  private boolean isEmpty(String fieldName, Object value) {
    if (value instanceof String) {
      return StringUtils.isEmpty(value);
    }
    if (value == null) {
      return true;
    }
    return false;
  }
}
