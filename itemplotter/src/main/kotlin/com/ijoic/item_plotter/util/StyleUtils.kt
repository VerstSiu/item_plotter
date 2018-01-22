package com.ijoic.item_plotter.util

import android.graphics.Canvas
import android.graphics.Rect
import android.view.Gravity
import android.view.ViewGroup
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
   * <p>Use ViewGroup.LayoutParams.MATCH_PARENT to specify match width or height.</p>
   *
   * @param bound bound.
   * @param style style.
   * @param width width.
   * @param height height.
   * @param outRect out rect.
   *
   * @see ViewGroup.LayoutParams.MATCH_PARENT
   */
  fun measureBlockRect(bound: Rect, style: BaseStyle, width: Int, height: Int, outRect: Rect) {
    val gravity = style.gravity
    val margin = style.margin

    val isMatchWidth = width == ViewGroup.LayoutParams.MATCH_PARENT
    val realWidth = Math.max(width, 0)
    val isMatchHeight = height == ViewGroup.LayoutParams.MATCH_PARENT
    val realHeight = Math.max(height, 0)

    // measure left and right position.
    val blockLeft: Int
    val blockRight: Int

    if (isMatchWidth) {
      blockLeft = bound.left + margin.left
      blockRight = bound.right - margin.right
    } else {
      blockLeft = when {
        isCenterHorizontal(gravity) -> bound.left + ((bound.width().toFloat() - realWidth.toFloat() + 0.5F).toInt() shr 1)
        containsFlag(gravity, Gravity.END) -> bound.right - realWidth - margin.right
        else -> bound.left + margin.left
      }
      blockRight = blockLeft + realWidth
    }

    // measure top and bottom position.
    val blockBottom: Int
    val blockTop: Int

    if (isMatchHeight) {
      blockBottom = bound.bottom - margin.bottom
      blockTop = bound.top + margin.top
    } else {
      blockBottom = when {
        isCenterVertical(gravity) -> bound.top + ((bound.height().toFloat() + realHeight.toFloat() + 0.5F).toInt() shr 1)
        containsFlag(gravity, Gravity.BOTTOM) -> bound.bottom - margin.bottom
        else -> bound.top + realHeight + margin.top
      }
      blockTop = blockBottom - realHeight
    }

    outRect.set(blockLeft, blockTop, blockRight, blockBottom)
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