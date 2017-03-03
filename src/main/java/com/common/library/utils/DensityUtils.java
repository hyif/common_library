package com.common.library.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 单位转换的辅助类
 *
 * @author: 黄一凡
 * @date: 2017-03-03
 */
public class DensityUtils {
  /**
   * dp转px
   */
  public static int dp2px(Context context, float dpVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        dpVal, context.getResources().getDisplayMetrics());
  }

  /**
   * sp转px
   */
  public static int sp2px(Context context, float spVal) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
        spVal, context.getResources().getDisplayMetrics());
  }

}
