/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.item_plotter.util

import android.graphics.Canvas
import android.graphics.Rect
import android.view.Gravity
import android.view.ViewGroup
import com.ijoic.item_plotter.style.PlotterStyle
import com.ijoic.item_plotter.style.BlockStyle
import com.ijoic.item_plotter.style.dimen.RoundDimen

/**
 * Style utils.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
object StyleUtils {

  /* Block */

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
  fun measureBlock(bound: Rect, style: PlotterStyle, width: Int, height: Int, outRect: Rect) {
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
        isAlignEnd(gravity) -> bound.right - realWidth - margin.right
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
        isAlignBottom(gravity) -> bound.bottom - margin.bottom
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
  fun measureBlock(bound: Rect, style: BlockStyle, outRect: Rect) {
    measureBlock(bound, style, style.width, style.height, outRect)
  }

  /**
   * Trim block.
   *
   * @param bound bound.
   * @param blockBound block bound.
   */
  fun trimBlock(bound: Rect, blockBound: Rect) {
    trimBlockHorizontal(bound, blockBound)
    trimBlockVertical(bound, blockBound)
  }

  /**
   * Trim block horizontal.
   *
   * @param bound bound.
   * @param blockBound block bound.
   */
  fun trimBlockHorizontal(bound: Rect, blockBound: Rect) {
    blockBound.apply {
      left = Math.max(bound.left, left)
      right = Math.min(bound.right, right)
    }
  }

  /**
   * Trim block vertical.
   *
   * @param bound bound.
   * @param blockBound block bound.
   */
  fun trimBlockVertical(bound: Rect, blockBound: Rect) {
    blockBound.apply {
      top = Math.max(bound.top, top)
      bottom = Math.min(bound.bottom, bottom)
    }
  }

  /**
   * Extend block.
   *
   * @param blockBound block bound.
   * @param gravity gravity.
   * @param width extend width.
   * @param height extend height.
   */
  fun extendBlock(blockBound: Rect, gravity: Int, width: Int, height: Int) {
    extendBlockHorizontal(blockBound, gravity, width)
    extendBlockVertical(blockBound, gravity, height)
  }

  /**
   * Extend block horizontal.
   *
   * @param blockBound block bound.
   * @param gravity gravity.
   * @param width extend width.
   */
  fun extendBlockHorizontal(blockBound: Rect, gravity: Int, width: Int) {
    if (width == 0) {
      return
    }
    val halfWidth = (width + 0.5F).toInt() shr 1

    when {
      isCenterHorizontal(gravity) -> {
        blockBound.left -= halfWidth
        blockBound.right = blockBound.right - halfWidth + width
      }
      isAlignEnd(gravity) -> blockBound.left -= width
      else -> blockBound.right += width
    }
  }

  /**
   * Extend block vertical.
   *
   * @param blockBound block bound.
   * @param gravity gravity.
   * @param height extend height.
   */
  fun extendBlockVertical(blockBound: Rect, gravity: Int, height: Int) {
    if (height == 0) {
      return
    }
    val halfHeight = (height + 0.5F).toInt() shr 1

    when {
      isCenterVertical(gravity) -> {
        blockBound.top -= halfHeight
        blockBound.bottom = blockBound.bottom - halfHeight + height
      }
      isAlignBottom(gravity) -> blockBound.top -= height
      else -> blockBound.bottom += height
    }
  }

  /* Render */

  /**
   * Draw contents with clip rect.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param renderItem render item.
   */
  fun drawAndClipBound(bound: Rect, canvas: Canvas, renderItem: () -> Unit) {
    if (!bound.isEmpty) {
      val restoreCount = canvas.save()

      if (canvas.clipRect(bound)) {
        renderItem.invoke()
      }
      canvas.restoreToCount(restoreCount)
    }
  }

  /**
   * Draw and clip padding.
   *
   * @param bound bound.
   * @param renderItem render item: fun(clipBound: Rect).
   * @param padding padding.
   */
  fun drawAndClipPadding(bound: Rect, renderItem: ((Rect) -> Unit)?, padding: RoundDimen) {
    if (renderItem == null) {
      return
    }
    if (padding.isEmpty) {
      renderItem.invoke(bound)
    } else{
      val clipBound = RectPool.obtainCopy(bound)
      padding.trimBound(clipBound)
      renderItem.invoke(clipBound)
      RectPool.release(clipBound)
    }
  }

  /* GravityHorizontal */

  /**
   * Returns center horizontal status.
   *
   * @param gravity gravity.
   */
  fun isCenterHorizontal(gravity: Int) = (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL

  /**
   * Returns align start status.
   *
   * @param gravity gravity.
   */
  fun isAlignStart(gravity: Int) = containsFlag(gravity, Gravity.START)

  /**
   * Returns align end status.
   *
   * @param gravity gravity.
   */
  fun isAlignEnd(gravity: Int) = containsFlag(gravity, Gravity.END)

  /* GravityVertical */

  /**
   * Returns center vertical status.
   *
   * @param gravity gravity.
   */
  fun isCenterVertical(gravity: Int) = (gravity and Gravity.VERTICAL_GRAVITY_MASK) == Gravity.CENTER_VERTICAL

  /**
   * Returns align top status.
   *
   * @param gravity gravity.
   */
  fun isAlignTop(gravity: Int) = containsFlag(gravity, Gravity.TOP)

  /**
   * Returns align bottom status.
   *
   * @param gravity gravity.
   */
  fun isAlignBottom(gravity: Int) = containsFlag(gravity, Gravity.BOTTOM)

  /**
   * Returns binary flag contains status.
   *
   * @param src flag set.
   * @param flag flag.
   */
  private fun containsFlag(src: Int, flag: Int) = (src and flag) == flag
}