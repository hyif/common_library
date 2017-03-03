package com.common.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关的工具类
 *
 * @author: 黄一凡
 * @date: 2017-03-03
 */
public class NetWorkUtils {
  /**
   * 判断当前网络是否可用
   */
  public static boolean isConnected(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE);
    if (null != connectivityManager) {
      NetworkInfo info = connectivityManager.getActiveNetworkInfo();
      if (null != info && info.isConnected()) {
        if (info.getState() == NetworkInfo.State.CONNECTED) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 判断当前是否为wifi网络环境
   */
  public static boolean isWifi(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
    if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return true;
    }
    return false;
  }
}
