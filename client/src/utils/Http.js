import axios from 'axios';
import CacheUtils from "./CacheUtils";


const HTTP_CONSTANT = {
  /**
   * 被过滤过滤器的非法请求,响应码为500.
   * <p>
   * 当某个请求被过滤器过滤时,理论上响应的状态应该是500
   * <p>
   * 但是为了前后端联调方便.
   * <p>
   * 该响应码仅在正式环境上作为默认的响应码使用
   */
  status_500: 500,
  /**
   * 被过滤过滤器的非法请求,响应码为500
   * <p>
   * 当某个请求被过滤器过滤时,理论上响应的状态应该是500
   * <p>
   * 但是为了前后端联调方便.
   * <p>
   * 该响应码仅在测试环境上作为指定的响应码使用
   *
   * @deprecated 仅在开发模式下此属性才用的到
   */
  status_405: 405,
  /**
   * 过期的访问权限
   */
  status_401: 401,
  /**
   * 访问的接口错误
   */
  status_404: 404,
  /**
   * 参数校验错误.
   * <p>
   * 意味者调用接口的人给定的参数不符合协定.
   */
  status_301: 301,
  /**
   * 程序代码错误.
   * <p>
   * 意味者代码编写错误.
   * <p>
   * 通常由于后台SQL编写错误,抛出的运行时异常,都使用这个状态码返回
   */
  status_302: 302,
  /**
   * 成功返回200
   */
  status_200: 200,
  /**
   * 默认常量_业务逻辑成功
   */
  code_success: 200,
  /**
   * 默认_业务逻辑失败
   */
  code_failed: 500,

}

let currentApi = null;
// 添加请求拦截器
axios.interceptors.request.use(req => {
    let url = req.url.substring(0, req.url.indexOf("?")).split("/");
    currentApi = {
      methodName: url[url.length - 1],
      serviceName: url[url.length - 2],
      servletName: url[url.length - 3],
    }
    if (!CacheUtils.isNoToken(currentApi)) {
      if (localStorage["token"]) {
        req.headers["token"] = localStorage["token"];
      }
      if (localStorage["uid"]) {
        req.headers["uid"] = localStorage["uid"];
      }
    }
    return req;
  }
  , error => {
    return Promise.reject(error);
  });

// 响应拦截器:统一前后端
axios.interceptors.response.use(res => {
  if (res.status >= HTTP_CONSTANT.status_401) {
    throw new Error(res.data);
  }
  if (res.status == HTTP_CONSTANT.status_301 || res.status == HTTP_CONSTANT.status_302) {
    vueObjectPoint.$message(res.data.msg);
    return;
  }
  if (res.status == HTTP_CONSTANT.status_200) {
    if (CacheUtils.isCanWriteToken(currentApi)) {
      if (res.headers.token) {
        localStorage["uid"] = res.headers.token;
      }
      if (res.headers.uid) {
        localStorage["uid"] = res.headers.uid;
      }
    }
  }
  return res;
}, err => {
  if (err.toString().indexOf('401') !== -1) {
    vueObjectPoint.$message('登录已过期，请重新登录！');
    vueObjectPoint.$router.push({name: '/'})
  } else if (err.toString().indexOf('404') !== -1) {
    vueObjectPoint.$message('您没有权限访问这个页面！')
    vueObjectPoint.$router.push({path: '/'})
  } else if (err.toString().indexOf('405') !== -1) {
    vueObjectPoint.$message(err.message)
    vueObjectPoint.$router.push({path: '/'})
  } else {
    vueObjectPoint.$message('服务器异常!');
  }
  return Promise.reject(err);
})

/**
 *
 *
 * @param servletName servletName
 * @param serviceName Action名
 * @param methodName 方法名
 * @param data Json表单数据
 * @returns {Promise}
 */
function get(api, data) {
  let params = "";
  for (let key in data) {
    params = params + ( key + "=" + data[key]) + "&";
  }
  if (params.length > 0) {
    params = params.substring(0, params.length - 1);
  }
  return new Promise(function (resolve, reject) {
    axios.get("http://192.168.100.104:8080/" + api.servletName + "/" + api.actionName + "/" + api.methodName + "?" + params, {
      validateStatus: function (status) {
        return status < 500; // 状态码在大于或等于500时才会 reject
      }
    }).then(res => {
      resolve(res);
    }).catch(err => {
      reject(err);
    })
  });
}

/**
 * vue 对象指针,需要初始化
 * @type {null}
 */
let vueObjectPoint = null;

export default {
  /**
   * Http get 方法.
   *
   * 简单的封装了Http get 请求
   * @param servletName servlet 的名称
   * @param serviceName 方法名
   * @param methodName 方法名
   * @param data Json格式的表单对象,当前函数仅对其进行第一层的遍历并组装成查询字符串.因此强烈要求表单设计为1层.否则表单参数将会丢失
   * @returns {Promise}
   */
  get: function (servletName, serviceName, methodName, data) {
    return get(servletName, serviceName, methodName, data);
  },
  /**
   * 初始化Http
   * 需要传入的是vue的实例对象
   * 主要用于在模块中调用vue上的挂载对象
   * @param vueObject vue 的实例对象
   */
  init: function (vueObject) {
    vueObjectPoint = vueObject;
  }
}
