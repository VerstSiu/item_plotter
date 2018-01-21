package com.ijoic.item_plotter.util

import android.graphics.Canvas
import android.graphics.Rect
import android.view.Gravity
import com.ijoic.item_plotter.style.BaseStyle
import com.ijoic.item_plotter.style.BlockStyle

/**
 * Style utils.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
object StyleUtils {
  /**
   * Measure block rect.
   *
   * @param bound bound.
   * @param style style.
   * @param width width.
   * @param height height.
   * @param outRect out rect.
   */
  fun measureBlockRect(bound: Rect, style: BaseStyle, width: Int, height: Int, outRect: Rect) {
    val gravity = style.gravity
    val boundLeft = bound.left
    val boundTop = bound.top

    val blockLeft = when {
      isCenterHorizontal(gravity) -> ((bound.width().toFloat() - width.toFloat() + 0.5F).toInt() shr 1)
      containsFlag(gravity, Gravity.END) -> bound.width() - width - boundLeft
      else -> boundLeft
    }
    val blockBottom = when {
      isCenterVertical(gravity) -> ((bound.height().toFloat() + height.toFloat() + 0.5F).toInt() shr 1)
      containsFlag(gravity, Gravity.BOTTOM) -> bound.height() - boundTop
      else -> height + boundTop
    }
    outRect.set(blockLeft, blockBottom - height, blockLeft + width, blockBottom)
  }

  /**
   * Measure block rect.
   *
   * @param bound bound.
   * @param style style.
   * @param outRect out rect.
   */
  fun measureBlockRect(bound: Rect, style: BlockStyle, outRect: Rect) {
    measureBlockRect(bound, style, style.width, style.height, outRect)
  }

  /**
   * Draw contents with clip rect.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param renderItem render item.
   */
  fun drawWithClipRect(bound: Rect, canvas: Canvas, renderItem: () -> Unit) {
    if (bound.isEmpty) {
      return
    }
    val restoreCount = canvas.save()

    if (canvas.clipRect(bound)) {
      renderItem.invoke()
    }
    canvas.restoreToCount(restoreCount)
  }

  /**
   * Returns binary flag contains status.
   *
   * @param src flag set.
   * @param flag flag.
   */
  private fun containsFlag(src: Int, flag: Int) = (src and flag) == flag

  /**
   * Returns center horizontal status.
   *
   * @param gravity gravity.
   */
  private fun isCenterHorizontal(gravity: Int) = (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL

  /**
   * Returns center vertical status.
   *
   * @param gravity gravity.
   */
  private fun isCenterVertical(gravity: Int) = (gravity and Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL
}