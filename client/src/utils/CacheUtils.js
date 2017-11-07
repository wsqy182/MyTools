import Constant from './Constant'

/**
 * 可以不用带token访问服务器的 列表
 */
var tokenTable = [{
  servletName: "api",
  serviceName: "user",
  methodName: "login"
}, {
  servletName: "api",
  serviceName: "user",
  methodName: "test"
}, {
  servletName: "api",
  serviceName: "see",
  methodName: "seeToken",
},
  {
    servletName: "api",
    serviceName: "see",
    methodName: "seeUser",
  }
]


var testApi = [];

if (Constant.isDev()) {
  testApi = [{
    servletName: "api",
    serviceName: "see",
    methodName: "seeToken",
  },
    {
      servletName: "api",
      serviceName: "see",
      methodName: "seeUser",
    }]
}
/**
 * 可以重写Token 的接口列表
 */
var writeTokenTable = [{
  servletName: "api",
  serviceName: "user",
  methodName: "login"
}];

/**
 * 是否为可写Token 的API
 * @param api
 * @returns {boolean}
 */
function isCanWriteToken(api) {
  if (!api) {
    return false;
  }
  for (let temp in writeTokenTable) {
    if (temp.servletName == api.servletName && temp.serviceName == api.methodName && temp.serviceName == api.serviceName) {
      return true;
    }
  }
  return false;
}

/**
 * 是否为免接口API
 * @param api
 * @returns {boolean}
 */
function isNoToken(api) {
  if (!api) {
    return false;
  }
  if (Constant.isDev()) {
    for (let test in testApi) {
      if (test.servletName == api.servletName && test.serviceName == api.methodName && test.serviceName == api.serviceName) {
        return true;
      }
    }
  }
  for (var temp in tokenTable) {
    if (temp.servletName == api.servletName && temp.serviceName == api.methodName && temp.serviceName == api.serviceName) {
      return true;
    }
  }
  return false;
}

export default {
  isCanWriteToken: function (api) {
    return isCanWriteToken(api);
  },
  isNoToken: function (api) {
    return isNoToken(api);
  }
}
