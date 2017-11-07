package com.xq.tool.mvc.form;

import com.xq.tool.core.annotation.Min;
import com.xq.tool.core.annotation.NotNull;
import com.xq.tool.core.annotation.Range;


/**
 * 分页查询表单
 */
public class QueryByPageForm extends BaseForm {

  public static final int desc = 1;
  public static final int asc = 2;

  @NotNull(filedName = "页码")
  @Min(filedName = "页码", minValue = 0)
  public int page;

  public String queryStr;
  public int queryType;

  /**
   * 默认值为1,即desc查询.
   * <p>
   * 取值范围参考QueryByPageForm的静态常量
   */
  @Range(left = 1, right = 2, fieldName = "排序类型")
  public int sortType = 1;


  private int rows = 0;

  public int getRows() {
    return 10;
  }

}
