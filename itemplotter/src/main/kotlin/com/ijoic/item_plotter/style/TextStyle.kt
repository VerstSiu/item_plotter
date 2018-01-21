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
import android.view.Gravity
import com.ijoic.item_plotter.util.PaintPool
import com.ijoic.item_plotter.util.RectPool
import com.ijoic.item_plotter.util.StyleUtils

/**
 * Text style.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class TextStyle: BaseStyle() {

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
   */
  fun drawText(bound: Rect, canvas: Canvas, text: String?, paint: Paint? = null) {
    if (text == null || text.isEmpty()) {
      return
    }
    val textPaint = paint ?: PaintPool.obtainTextPaint()
    textPaint.color = textColor
    textPaint.textSize = textSize
    textPaint.typeface = typeface

    // draw text.
    if (textColor !=  Color.TRANSPARENT) {
      drawTextWithDetail(bound, canvas, text, textPaint, gravity, offsetX, offsetY)
    }
    if (paint == null) {
      PaintPool.release(textPaint)
    }
  }
  /**
   * Draw text.
   *
   * <p>Draw text content according to gravity and x, y offset.</p>
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param text text.
   * @param paint paint.
   * @param gravity gravity.
   * @param offsetX offset x.
   * @param offsetY offset y.
   */
  private fun drawTextWithDetail(bound: Rect, canvas: Canvas, text: String, paint: Paint, gravity: Int = Gravity.CENTER, offsetX: Int = 0, offsetY: Int = 0) {
    val measureRect = RectPool.obtain()
    paint.textAlign = Paint.Align.LEFT
    paint.getTextBounds(text, 0, text.length, measureRect)

    val textInitLeft = -measureRect.left + bound.left
    val textInitTop = -measureRect.bottom + bound.top
    val textWidth = measureRect.width()
    val textHeight = measureRect.height()
    RectPool.release(measureRect)

    val blockRect = RectPool.obtain()
    StyleUtils.measureBlockRect(bound, this, textWidth, textHeight, blockRect)

    val textLeft = textInitLeft + blockRect.left
    val textBottom = textInitTop + blockRect.bottom
    RectPool.release(blockRect)

    canvas.drawText(text, textLeft.toFloat(), textBottom.toFloat(), paint)
  }

}