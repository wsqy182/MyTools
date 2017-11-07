package com.xq.tool.core.utils;

import java.security.MessageDigest;
import java.util.UUID;

public class OtherUtils {
  /**
   * MD5解析失败时,将返回一个32位长的UUID
   *
   * @param str
   * @return
   */
  public static String getMd5OrUUID(String str) {
    String result = "";
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
      md5.update((str).getBytes("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
      return UUID.randomUUID().toString().replace("-", "");
    }
    byte b[] = md5.digest();
    int i;
    StringBuffer buf = new StringBuffer("");
    for (int offset = 0; offset < b.length; offset++) {
      i = b[offset];
      if (i < 0) {
        i += 256;
      }
      if (i < 16) {
        buf.append("0");
      }
      buf.append(Integer.toHexString(i));
    }
    result = buf.toString();
    return result;
  }

}
