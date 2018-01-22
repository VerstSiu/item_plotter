package com.ijoic.item_plotter.util

import android.graphics.Paint

/**
 * RectF pool.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
object PaintPool : InstancePool<Paint>({ Paint() }) {
  override fun onReleaseElement(instance: Paint) {
    instance.reset()
  }

  /**
   * Returns text paint instance.
   */
  fun obtainTextPaint() = obtain().apply {
    style = Paint.Style.FILL
    isDither = true
    isAntiAlias = true
  }

  /**
   * Returns fill paint.
   */
  fun obtainFillPaint() = obtain().apply {
    style = Paint.Style.FILL
  }
}