package com.ijoic.item_plotter.util

import android.view.View

/**
 * Measure utils.
 *
 * @author xiao.yl on 2018/1/24.
 * @version 1.0
 */
object MeasureUtils {

  /**
   * Measure half int.
   *
   * @param value int value.
   */
  fun getHalfInt(value: Int) = ((value.toFloat() + 0.5F).toInt() shr 1)

  /**
   * Toggle spec mode.
   *
   * @param measureSpec measure spec.
   * @param replaceMode replace spec mode.
   *
   * @see View.MeasureSpec.EXACTLY
   * @see View.MeasureSpec.AT_MOST
   * @see View.MeasureSpec.UNSPECIFIED
   */
  fun toggleSpecMode(measureSpec: Int, replaceMode: Int): Int {
    return View.MeasureSpec.makeMeasureSpec(
        View.MeasureSpec.getSize(measureSpec),
        replaceMode
    )
  }

  /**
   * Replace exactly measure spec with at most.
   *
   * @param measureSpec measure spec.
   */
  fun exactly2atMost(measureSpec: Int) = when(View.MeasureSpec.getMode(measureSpec)) {
    View.MeasureSpec.EXACTLY -> toggleSpecMode(measureSpec, View.MeasureSpec.AT_MOST)
    View.MeasureSpec.AT_MOST -> measureSpec
    View.MeasureSpec.UNSPECIFIED -> measureSpec
    else -> measureSpec
  }
}