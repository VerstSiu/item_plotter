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
import com.ijoic.item_plotter.util.PaintPool
import com.ijoic.item_plotter.util.RectPool
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
    val fillHeight = Math.max(textSize.toInt() - textBound.height(), 0)
    val textWidth = textBound.width() + baseWidth
    val textHeight = textBound.height() + baseHeight + fillHeight

    val blockRect = RectPool.obtain()
    StyleUtils.measureBlock(bound, this, textWidth, textHeight, blockRect)

    // draw text
    StyleUtils.drawAndClipPadding(blockRect, {
      val fillHeightHalf = if (fillHeight == 0) 0 else ((fillHeight + 0.5F).toInt() shr 1)
      val textLeft = textInitLeft + it.left
      val textBottom = textInitTop + it.bottom + fillHeightHalf

      if (clipPadding) {
        StyleUtils.drawAndClipBound(it, canvas, {
          canvas.drawText(text, textLeft.toFloat(), textBottom.toFloat(), textPaint)
        })
      } else {
        canvas.drawText(text, textLeft.toFloat(), textBottom.toFloat(), textPaint)
      }
    }, padding)

    renderAppend?.invoke(blockRect)
    RectPool.release(blockRect)

    PaintPool.checkNullRelease(paint, textPaint)
  }
}