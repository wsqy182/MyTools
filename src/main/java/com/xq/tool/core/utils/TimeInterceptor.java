package com.xq.tool.core.utils;

import com.xq.tool.core.annotation.CheckParam;
import com.xq.tool.core.mvc.MyHttpRequest;
import com.xq.tool.core.mvc.MyHttpResponse;
import com.xq.tool.mvc.form.BaseForm;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 检测方法执行耗时的spring切面类
 * 使用@Aspect注解的类，Spring将会把它当作一个特殊的Bean（一个切面），也就是不对这个类本身进行动态代理
 *
 * @author blinkfox
 * @date 2016-07-04
 */
@Aspect
@Component
public class TimeInterceptor {
  // service层的统计耗时切面，类型必须为final String类型的,注解里要使用的变量只能是静态常量类型的
  // action层的统计耗时切面
  public static final String POINT = "execution (* com.xq.tool.mvc.action.*Action.*(..))";

  // 一分钟，即1000ms
  private static final long ONE_MINUTE = 1000;
  private static Logger logger = Logger.getLogger(TimeInterceptor.class);


  /**
   * 首字母转大写
   *
   * @param s
   * @return
   */
  public static String toUpperCaseFirstOne(String s) {
    if (Character.isUpperCase(s.charAt(0)))
      return s;
    else
      return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
  }

  /**
   * 统计方法执行耗时Around环绕通知
   *
   * @param joinPoint
   * @return
   */
  @Around(POINT)
  public Object timeAround(ProceedingJoinPoint joinPoint) {
    // 定义返回对象、得到方法需要的参数
    Object result = null;
    Object[] args = joinPoint.getArgs();
    MyHttpRequest request = (MyHttpRequest) args[0];
    MyHttpResponse response = (MyHttpResponse) args[1];
    Map<String, Object> param = request.getParameterMap();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
    Method method = signature.getMethod();
    if (method.isAnnotationPresent(CheckParam.class)) {
      Class cls = null;
      BaseForm baseForm = null;
      try {
        CheckParam annotation = method.getAnnotation(CheckParam.class);
        String formName = annotation.formName();
        cls = Class.forName("com.xq.tool.mvc.form." + formName + "Form");//加载UserBean类到内存中！获取一个Class对象
        baseForm = (BaseForm) cls.newInstance();//通过class类反射一个对象实体！
        if (!baseForm.init(baseForm, param)) { // 初始化失败 ,返回
          response.failed_301("请求的参数错误!");
          return result;
        }
        if (!baseForm.Validate()) { // 参数校验不通过,返回.
          response.failed_301(baseForm.getM_err_msg());
          return result;
        }
        request.setForm(baseForm);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    long startTime = System.currentTimeMillis();
    try {
      result = joinPoint.proceed(args);
    } catch (Throwable e) {
      logger.error("统计某方法执行耗时环绕通知出错", e);
    }
    // 获取执行的方法名
    long endTime = System.currentTimeMillis();
    // 打印耗时的信息
    this.printExecTime(methodName, startTime, endTime);
    return result;
  }

  /**
   * 打印方法执行耗时的信息
   *
   * @param methodName
   * @param startTime
   * @param endTime
   */
  private void printExecTime(String methodName, long startTime, long endTime) {
    long diffTime = endTime - startTime;
    if (diffTime > ONE_MINUTE) {
      logger.warn(methodName + " 方法执行耗时：" + diffTime + " ms");
    } else {
      logger.info(methodName + " 方法执行耗时：" + diffTime + " ms");
    }
  }
}