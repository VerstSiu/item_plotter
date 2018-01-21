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
package com.ijoic.item_plotter.plotter

import android.graphics.*
import android.view.Gravity
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.util.RenderUtils

/**
 * Text plotter.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class TextPlotter: BasePlotter() {

  /* Properties */

  /**
   * Text.
   */
  var text: String? = null

  /**
   * Text color.
   */
  var textColor: Int = Color.BLACK

  /**
   * Background color.
   */
  var backgroundColor: Int = Color.TRANSPARENT

  /**
   * Text size.
   */
  var textSize: Float = 24F

  /**
   * Gravity.
   */
  var gravity: Int = Gravity.CENTER

  /**
   * Offset x.
   *
   * <p>Used only when gravity is not CENTER_HORIZONTAL.</p>
   */
  var offsetX: Int = 0

  /**
   * Offset y.
   *
   * <p>Used only when gravity is not CENTER_VERTICAL.</p>
   */
  var offsetY: Int = 0

  /**
   * Type face.
   */
  var typeface: Typeface? = null

  /* Draw */

  private val paint: Paint = Paint()

  override fun onDraw(bound: Rect, itemData: ItemData?, canvas: Canvas) {
    val text = getBindString(itemData, this.text)

    paint.color = textColor
    paint.style = Paint.Style.FILL
    paint.textSize = textSize
    paint.typeface = typeface
    paint.isAntiAlias = true
    paint.isDither = true

    // draw background.
    if (backgroundColor != Color.TRANSPARENT) {
      canvas.drawColor(backgroundColor)
    }

    // draw text.
    if (textColor !=  Color.TRANSPARENT) {
      RenderUtils.drawText(canvas, paint, text, bound, gravity, offsetX, offsetY)
    }
  }
}