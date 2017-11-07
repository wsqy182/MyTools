package com.xq.tool.mvc.dao;

import com.xq.tool.core.utils.BaseDao;
import com.xq.tool.mvc.entity.AccountsVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao extends BaseDao<AccountsVO> {
  /**
   * 登录查询
   *
   * @param accounts
   * @param password
   * @return
   */
  public AccountsVO login(String accounts, String password) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("username", accounts);
    param.put("password", password);
    String hql = "from AccountsVO where username=:username and password=:password";
    List<AccountsVO> list = find(hql, param);
    if (list != null && list.size() == 1) {
      return list.get(0);
    }
    return null;
  }
}
