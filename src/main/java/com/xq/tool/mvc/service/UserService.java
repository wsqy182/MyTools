package com.xq.tool.mvc.service;

import com.xq.tool.mvc.form.LoginForm;
import com.xq.tool.core.mvc.TheCache;
import com.xq.tool.mvc.dao.UserDao;
import com.xq.tool.mvc.entity.AccountsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

  @Autowired
  UserDao userDao;

  /**
   * @return
   */
  public AccountsVO login(LoginForm loginForm) {
    AccountsVO loginUser = this.userDao.login(loginForm.getAccounts(), loginForm.getPassword());
    if (loginUser != null) {
      TheCache.putPubUser(loginUser);
    }
    return loginUser;
  }
}
