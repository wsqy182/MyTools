package com.xq.tool.core.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @author MACHENIKE
 */
public class JWTTool extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static String key = "xq123456";


  /**
   * 常规的token校验,仅仅校验token的合法性判断用户是否有权限
   * 更详细的权限控制,参见ValidateConfig
   * 验证token合法性,和getToken的算法是反向算法 返回一个token合法性的JSON结果:{ msg:'',//相关信息
   * auth:false//授权是否成功的标志 }
   */
  public static JSONObject validateToken(String token) {
    JSONObject result = new JSONObject();
    if (token == null || "".equals(token)) {
      result.put("auth", false);
      result.put("msg", "token不存在");
      return result;
    }
    /**
     * .号是特殊字符,需要转义\\.才能正确分割
     */
    String[] jwt = token.split("\\.");
    if (jwt.length != 3) {
      result.put("auth", false);
      result.put("msg", "非法数据");
      return result;
    }
    String header = null;
    String payload = null;
    String signatureByToken = jwt[2];
    try {
      header = new String(Base64Utils.decodeFromUrlSafeString(jwt[0]), "UTF-8");
      payload = new String(Base64Utils.decodeFromUrlSafeString(jwt[1]), "UTF-8");
      String signatureByHash = HMACSHA256((jwt[0] + "." + jwt[1]).getBytes(), key.getBytes());
      if (!signatureByHash.equals(signatureByToken)) {
        result.put("auth", false);
        result.put("msg", "token被篡改");
        return result;
      }
    } catch (IOException e) {
      e.printStackTrace();
      result.put("auth", false);
      result.put("msg", "验证失败");
      return result;
    }
    result.put("auth", true);
    result.put("msg", "验证成功");
    result.put("payload", payload.toString());
    return result;
  }

  /**
   * 获取payload
   *
   * @param token 欲解析的token
   * @return 返回token中的载体信息, 解析失败返回null
   */
  public static JSONObject getPayload(String token) {
    JSONObject payload = JWTTool.validateToken(token);
    if (payload != null) {
      return payload.getJSONObject("payload");
    } else {
      return null;
    }
  }

  /**
   * HMACSHA256算法:
   * 参考文档:http://blog.csdn.net/u012417984/article/details/49837411
   */
  public static String HMACSHA256(byte[] data, byte[] key) {
    try {
      SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
      Mac mac = Mac.getInstance("HmacSHA256");
      mac.init(signingKey);
      return byte2hex(mac.doFinal(data));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 字节数组转换为字符串
   */
  public static String byte2hex(byte[] b) {
    StringBuilder hs = new StringBuilder();
    String stmp;
    for (int n = 0; b != null && n < b.length; n++) {
      stmp = Integer.toHexString(b[n] & 0XFF);
      if (stmp.length() == 1)
        hs.append('0');
      hs.append(stmp);
    }
    return hs.toString().toUpperCase();
  }

  /**
   * 获取token
   *
   * @param userId     token 所挂在的对象
   * @param createTime token生成的时间
   * @param howTime    token有效时间
   * @return
   * @throws UnsupportedEncodingException
   */
  public static String getToken(Integer userId, long createTime, long howTime) throws UnsupportedEncodingException {
    JSONObject headerJson = new JSONObject();
    String headerStr = "", payloadStr = "", signature = "";
    // 1.生成header
    // type:标识token类型
    headerJson.put("typ", "JWT");
    // algorithm:摘要算法
    headerJson.put("alg", "HS256");
    // 编码生成base64Url字符串
    headerStr = Base64Utils.encodeToUrlSafeString(headerJson.toString().getBytes());

    // 2.要负载的信息
    JSONObject payloadJson = new JSONObject();
    // 以下claim的key为JWT 保留字
    // 保证uid 唯一性
    String uuid = UUID.randomUUID().toString().replace("-", "");
    payloadJson.put("rd", uuid);
    // 签发主体
    payloadJson.put("iss", "com.xq.mytools.web");
    // 过期时间
    payloadJson.put("exp", createTime + howTime);
    // 签发时间
    payloadJson.put("iat", createTime);
    // 编码生成base64Url字符串
    payloadStr = Base64Utils.encodeToUrlSafeString(payloadJson.toString().getBytes());

    // 3.signature 生成
    // HS256其实包含两种算法：HMC算法(生成摘要)和SHA256算法(对摘要进行数字签名)
    signature = HMACSHA256((headerStr + "." + payloadStr).getBytes(), key.getBytes());

    String token = headerStr + "." + payloadStr + "." + signature;
    return token;
  }

  public static String getUid(String token) {
    JSONObject payload = getPayload(token);
    if (payload != null) {
      return payload.getString("uid");
    } else {
      return null;
    }
  }

}