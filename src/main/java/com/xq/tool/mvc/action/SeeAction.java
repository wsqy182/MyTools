package com.xq.tool.mvc.action;

import com.xq.tool.core.mvc.MyHttpRequest;
import com.xq.tool.core.mvc.MyHttpResponse;
import com.xq.tool.core.mvc.TheCache;
import com.xq.tool.core.utils.Constant;
import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.entity.TokenVO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当前组件中所有的接口仅用于测试登录
 */
@Component
public class SeeAction {

  /**
   * 查看当前用户列表
   *
   * @param req
   * @param res
   */
  public void seeToken(MyHttpRequest req, MyHttpResponse res) throws IOException {
    if (Constant.isDev) {
      Map<Integer, TokenVO> map = TheCache.getTokenMap();
      List<TokenVO> list = new ArrayList<TokenVO>();
      for (Integer i : map.keySet()) {
        list.add(map.get(i));
      }
      res.success(list);
    }
  }

  /**
   * 查看当前Token列表
   *
   * @param req
   * @param res
   */
  public void seeUser(MyHttpRequest req, MyHttpResponse res) throws IOException {
    if (Constant.isDev) {
      Map<Integer, AccountsVO> map = TheCache.getUserMap();
      List<AccountsVO> list = new ArrayList<AccountsVO>();
      for (Integer i : map.keySet()) {
        list.add(map.get(i));
      }
      res.success(list);
    }
  }

}
