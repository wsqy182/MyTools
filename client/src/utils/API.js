export default {
  /**
   * 用户登录接口
   * @returns {{servletName: string, methodName: string, actionName: string}}
   */
  userLogin: function () {
    return {
      servletName: "api",
      actionName: "user",
      methodName: "login"
    }
  },
  /**
   * 查看Token接口
   * @returns {{servletName: string, methodName: string, actionName: string}}
   */
  seeToken: function () {
    return {
      servletName: "api",
      actionName: "see",
      methodName: "seeToken"
    }
  },
  /**
   * 查看用户接口
   * @returns {{servletName: string, methodName: string, actionName: string}}
   */
  seeUser: function () {
    return {
      servletName: "api",
      actionName: "see",
      methodName: "seeUser"
    }
  }
}
