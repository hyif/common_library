package com.common.library.utils;

import android.os.Environment;

import java.io.File;

/**
 * SD卡辅助类
 *
 * @author: 黄一凡
 * @date: 2017-03-03
 */
public class SDCardUtils {
  /**
   * 判断SDCard是否可用
   */
  public static boolean isSDCardEnable() {
    return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
  }

  /**
   * 获取SD卡路径
   */
  public static String getSDCardPath() {
    return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
  }
}
