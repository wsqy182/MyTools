package com.xq.tool.core.mvc;

import com.xq.tool.core.utils.Constant;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Component
public class NoTokenTable {
  private static List<Api> noTokenTable;

  @PostConstruct
  public static void init() {
    noTokenTable = new ArrayList<Api>();

    //-------------------------
    Api testApi = new Api();
    testApi.setServletName("api");
    testApi.setServiceName("user");
    testApi.setMethodName("test");
    noTokenTable.add(testApi);

    //-------------------------
    Api loginApi = new Api();
    testApi.setServletName("api");
    testApi.setServiceName("user");
    testApi.setMethodName("login");
    noTokenTable.add(loginApi);

    //-------------------------
    if (Constant.isDev) {
      // 开发环境下这两个接口免token
      Api seeUserApi = new Api();
      testApi.setServletName("api");
      testApi.setServiceName("see");
      testApi.setMethodName("seeUser");
      noTokenTable.add(seeUserApi);

      //-------------------------
      Api seeToken = new Api();
      testApi.setServletName("api");
      testApi.setServiceName("see");
      testApi.setMethodName("seeToken");
      noTokenTable.add(loginApi);
    }

  }

  /**
   * 查询某个接口是否为免接口的接口.
   *
   * @return
   */
  public static boolean isNoToken(Api param) {
    for (Api api : noTokenTable) {
      if (api.getServletName().equals(param.getServletName())
          && api.getServiceName().equals(param.getServiceName())
          && api.getMethodName().equals(param.getMethodName())) {
        return true;
      }
    }
    return false;
  }
}
