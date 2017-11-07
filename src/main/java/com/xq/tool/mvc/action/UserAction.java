package com.xq.tool.mvc.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xq.tool.core.annotation.CheckParam;
import com.xq.tool.core.mvc.MyHttpRequest;
import com.xq.tool.core.mvc.MyHttpResponse;
import com.xq.tool.core.mvc.TheCache;
import com.xq.tool.core.utils.Constant;
import com.xq.tool.core.utils.JWTTool;
import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.entity.TokenVO;
import com.xq.tool.mvc.form.LoginForm;
import com.xq.tool.mvc.form.QueryByPageForm;
import com.xq.tool.mvc.service.OtherAccountsService;
import com.xq.tool.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Created by MACHENIKE on 2017/10/18.
 */
@Component
public class UserAction {
  @Autowired
  UserService userService;
  @Autowired
  OtherAccountsService otherAccountsService;

  /**
   * @param req
   * @param res
   */
  public void test(MyHttpRequest req, MyHttpResponse res) throws IOException {
    res.success("fuck spring!");
  }

  /**
   * 登录方法
   *
   * @param req
   * @param res
   */
  @CheckParam(formName = "Login")
  public void login(MyHttpRequest req, MyHttpResponse res) throws IOException {
    LoginForm loginForm = (LoginForm) req.getForm();
    AccountsVO loginUser = this.userService.login(loginForm);
    JSONObject data = new JSONObject();
    if (loginUser != null) {
      // 1.准备用户数据
      data.put("userId", loginUser.getUserId());
      data.put("nickname", loginUser.getNickname());
      data.put("nickname", loginUser.getUsername());
      res.success(data);
      // 2.准备授权信息
      TokenVO tokenVO = new TokenVO();
      tokenVO.setUserId(loginUser.getUserId());
      tokenVO.setCreateDate(new Date());
      long expressTime = 0;
      if (Constant.isDev) {// 线下为24个小时有效期
        expressTime = 24 * 60 * 60 * 1000;
      } else {// 线上为半个小时有效期
        expressTime = 30 * 60 * 1000;
      }
      // 2.1过期时间为30分钟
      tokenVO.setValidTime((int) expressTime);
      // 3.生成token令牌
      JSONObject token = (JSONObject) JSON.toJSON(tokenVO);
      String tokenStr = JWTTool.getToken(loginUser.getUserId(), tokenVO.getCreateDate().getTime(), expressTime);
      // 4.缓存token 令牌
      tokenVO.setToken(tokenStr);
      TheCache.putToken(tokenVO);
      // 5.设置响应头
      res.setToken(tokenStr);
      res.setHeader("uid", loginUser.getUserId() + "");
      res.setHeader("Access-Control-Expose-Headers", "token,uid");
    } else {
      res.failed("用户名或者密码错误!请重新输入!");
    }
    return;
  }

  /**
   * 获取用户的第三方网站记录
   *
   * @param req
   * @param res
   * @throws IOException
   */
  @CheckParam(formName = "QueryByPage")
  public void getThirdSiteInfo(MyHttpRequest req, MyHttpResponse res) throws IOException {
    QueryByPageForm form = (QueryByPageForm) req.getForm();
    System.out.println(JSONObject.toJSONString(req.getCurrentUser()));
    JSONArray data = (JSONArray) JSONArray.toJSON(this.otherAccountsService.getUserOtherAccounts(req.getCurrentUser(), form));
    res.success(data);
  }
}
