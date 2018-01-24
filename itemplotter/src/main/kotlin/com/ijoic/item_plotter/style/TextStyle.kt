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
package com.ijoic.item_plotter.style

import android.graphics.*
import com.ijoic.item_plotter.util.MeasureUtils
import com.ijoic.item_plotter.util.pool.PaintPool
import com.ijoic.item_plotter.util.pool.RectPool
import com.ijoic.item_plotter.util.StyleUtils

/**
 * Text style.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class TextStyle: PlotterStyle() {

  /**
   * Text color.
   */
  var textColor: Int = Color.BLACK

  /**
   * Text size.
   */
  var textSize: Float = 24F

  /**
   * Type face.
   */
  var typeface: Typeface? = null

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param text text.
   * @param paint text paint.
   * @param renderAppend render append: fun(textBound: Rect).
   * @param clipPadding clip padding content or not.
   */
  fun drawText(bound: Rect, canvas: Canvas, text: String?, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null, clipPadding: Boolean = false) {
    val textPaint = paint ?: PaintPool.obtainSmoothFillPaint()
    val textBound = RectPool.obtain()

    measureTextBound(text, textBound, paint)
    drawTextWithTextBound(bound, canvas, text, textBound, paint, renderAppend, clipPadding)

    PaintPool.checkNullRelease(paint, textPaint)
    RectPool.release(textBound)
  }

  /**
   * Measure text bound.
   *
   * @param text text.
   * @param textBound text bound.
   * @param paint text paint.
   */
  fun measureTextBound(text: String?, textBound: Rect, paint: Paint? = null) {
    if (text == null || text.isEmpty()) {
      textBound.setEmpty()
      return
    }
    val textPaint = paint ?: PaintPool.obtainSmoothFillPaint()

    textPaint.textAlign = Paint.Align.LEFT
    textPaint.textSize = textSize
    textPaint.typeface = typeface
    textPaint.getTextBounds(text, 0, text.length, textBound)

    PaintPool.checkNullRelease(paint, textPaint)
  }

  /**
   * Draw text wit text bound.
   *
   * <p>Draw text content align text size height center.</p>
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param text text.
   * @param textBound text bound.
   * @param paint text paint.
   * @param renderAppend render append: fun(textBound: Rect).
   * @param clipPadding clip padding content or not.
   */
  fun drawTextWithTextBound(bound: Rect, canvas: Canvas, text: String?, textBound: Rect, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null, clipPadding: Boolean = false) {
    val textColor = this.textColor
    val baseWidth = padding.left + padding.right
    val baseHeight = padding.top + padding.bottom
    val textSize = this.textSize
    val textSizeInt = textSize.toInt()

    if (text == null || text.isEmpty() || textBound.isEmpty || textColor == Color.TRANSPARENT) {
      // always run render append, even if background is not required to render.
      if (renderAppend != null) {
        val blockRect = RectPool.obtain()
        StyleUtils.measureBlock(bound, this, baseWidth, baseHeight, blockRect)
        renderAppend.invoke(blockRect)
        RectPool.release(blockRect)
      }
      return
    }
    val textPaint = paint ?: PaintPool.obtainSmoothFillPaint()
    textPaint.textAlign = Paint.Align.LEFT
    textPaint.color = textColor
    textPaint.textSize = textSize
    textPaint.typeface = typeface

    // draw text.
    val textInitLeft = -textBound.left
    val textInitTop = -textBound.bottom
    val textWidth = textBound.width() + baseWidth
    val textHeight = textSizeInt + baseHeight

    val blockRect = RectPool.obtain()
    StyleUtils.measureBlock(bound, this, textWidth, textHeight, blockRect)

    val extraHeight = Math.max(textSizeInt - textBound.height(), 0)
    val extraHeightHalf = if (extraHeight == 0) 0 else MeasureUtils.getHalfInt(extraHeight)

    // draw text
    StyleUtils.drawAndClipPadding(blockRect, {
      val textLeft = textInitLeft + it.left
      val textBottom = textInitTop + it.bottom - MeasureUtils.getHalfInt(it.height() - textBound.height())

      if (clipPadding) {
        StyleUtils.drawAndClipBound(it, canvas, {
          canvas.drawText(text, textLeft.toFloat(), textBottom.toFloat(), textPaint)
        })
      } else {
        canvas.drawText(text, textLeft.toFloat(), textBottom.toFloat(), textPaint)
      }
    }, padding, offsetY = extraHeightHalf)

    renderAppend?.invoke(blockRect)
    RectPool.release(blockRect)

    PaintPool.checkNullRelease(paint, textPaint)
  }
}