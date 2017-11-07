package com.xq.tool.mvc.service;

import com.xq.tool.mvc.entity.AccountsVO;
import com.xq.tool.mvc.form.QueryByPageForm;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherAccountsService extends BaseService {

  /**
   * 获取用户第三方网站帐号密码
   */
  public Object getUserOtherAccounts(AccountsVO currentUser, QueryByPageForm queryByPageForm) {
    String sql = "select " +
        "recordId," +
        "password," +
        "accounts," +
        "bindTelPhone," +
        "bindEmail," +
        "siteName," +
        "siteUrl," +
        "mark," +
        "createDate from Other_Accounts  where userId=:userId ";
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("userId", currentUser.getUserId());
    String where = "";
    if (!StringUtils.isEmpty(queryByPageForm.queryStr)) {
      where = " and accounts='%accounts%' ";
    }
    String order;
    if (queryByPageForm.sortType == QueryByPageForm.desc) { //desc
      order = " order by createDate desc";
    } else {
      order = " order by createDate ";
    }
    sql = sql + where + order;
    List<Map<String, Object>> result = this.baseDao.findByPageForMap(sql, queryByPageForm.page, queryByPageForm.getRows(), param);
    return result;
  }
}
