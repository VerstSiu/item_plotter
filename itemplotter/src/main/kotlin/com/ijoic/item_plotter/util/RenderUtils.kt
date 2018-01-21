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
import android.graphics.Paint
import android.graphics.Rect
import android.view.Gravity

/**
 * Render utils.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
object RenderUtils {

  /* Paint */

  /**
   * Returns new text paint instance.
   */
  fun newTextPaint() = Paint().apply {
    style = Paint.Style.FILL
    isDither = true
    isAntiAlias = true
  }

  /* DrawText */

  /**
   * Draw text.
   *
   * <p>Draw text content according to gravity and x, y offset.</p>
   *
   * @param canvas canvas.
   * @param paint paint.
   * @param text text.
   * @param bound bound.
   * @param gravity gravity.
   * @param offsetX offset x.
   * @param offsetY offset y.
   */
  fun drawText(canvas: Canvas, paint: Paint, text: String?, bound: Rect, gravity: Int = Gravity.CENTER, offsetX: Int = 0, offsetY: Int = 0) {
    if (text == null || text.isEmpty()) {
      return
    }
    val measureRect = RectPool.obtain()
    paint.textAlign = Paint.Align.LEFT
    paint.getTextBounds(text, 0, text.length, measureRect)

    val textInitLeft = -measureRect.left + bound.left
    val textInitTop = -measureRect.bottom + bound.top
    val textWidth = measureRect.width()
    val textHeight = measureRect.height()
    RectPool.release(measureRect)

    val drawLeft = when {
      isCenterHorizontal(gravity) -> textInitLeft + ((bound.width().toFloat() - textWidth.toFloat() + 0.5F).toInt() shr 1)
      containsFlag(gravity, Gravity.END) -> textInitLeft + bound.width() - textWidth - offsetX
      else -> textInitLeft + offsetX
    }
    val drawBottom = when {
      isCenterVertical(gravity) -> textInitTop + ((bound.height().toFloat() + textHeight.toFloat() + 0.5F).toInt() shr 1)
      containsFlag(gravity, Gravity.BOTTOM) -> textInitTop + bound.height() - offsetY
      else -> textInitTop + textHeight + offsetY
    }

    canvas.drawText(text, drawLeft.toFloat(), drawBottom.toFloat(), paint)
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