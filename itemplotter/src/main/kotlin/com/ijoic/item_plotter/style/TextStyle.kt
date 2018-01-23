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
    val textColor = this.textColor
    val baseWidth = padding.left + padding.right
    val baseHeight = padding.top + padding.bottom

    if (text == null || text.isEmpty() || textColor == Color.TRANSPARENT) {
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
    textPaint.color = textColor
    textPaint.textSize = textSize
    textPaint.typeface = typeface

    // draw text.
    val measureRect = RectPool.obtain()
    textPaint.textAlign = Paint.Align.LEFT
    textPaint.getTextBounds(text, 0, text.length, measureRect)

    val textInitLeft = -measureRect.left
    val textInitTop = -measureRect.bottom
    val textWidth = measureRect.width() + baseWidth
    val textHeight = measureRect.height() + baseHeight
    RectPool.release(measureRect)

    val blockRect = RectPool.obtain()
    StyleUtils.measureBlock(bound, this, textWidth, textHeight, blockRect)

    // draw text
    StyleUtils.drawAndClipPadding(blockRect, {
      val textLeft = textInitLeft + it.left
      val textBottom = textInitTop + it.bottom

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

    if (paint == null) {
      PaintPool.release(textPaint)
    }
  }

}