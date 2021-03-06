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

import android.content.Context
import android.graphics.*
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.style.TextStyle

/**
 * Text plotter.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class TextPlotter: Plotter() {
  /**
   * Text.
   */
  var text: String? = null

  /**
   * Text style.
   */
  val textStyle = TextStyle()

  override fun measureMinWidth() = appendExpectedMinWidth(super.measureMinWidth(), textStyle)

  override fun measureMinHeight() = appendExpectedMinHeight(super.measureMinHeight(), textStyle)

  /* Draw */

  override fun onDraw(context: Context, bound: Rect, canvas: Canvas, itemData: ItemData?) {
    textStyle.drawText(bound, canvas, getBindString(itemData, unbindReplace = this.text))
  }
}