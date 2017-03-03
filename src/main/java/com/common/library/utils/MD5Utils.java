package com.common.library.utils;

import java.security.MessageDigest;

/**
 * MD5辅助类
 *
 * @author: 黄一凡
 * @date: 2017-03-03
 */
public class MD5Utils {
  public static String md5(String s) {
    try {
      byte[] btInput = s.getBytes();
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < md.length; i++) {
        int val = ((int) md[i]) & 0xff;
        if (val < 16) {
          sb.append("0");
        }
        sb.append(Integer.toHexString(val));

      }
      return sb.toString();
    } catch (Exception e) {
      return null;
    }
  }
}