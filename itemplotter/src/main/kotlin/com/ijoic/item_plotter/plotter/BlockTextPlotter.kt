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

import android.graphics.Canvas
import android.graphics.Rect
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.style.BlockTextStyle

/**
 * Block text plotter.
 *
 * <p>Draw text content inside a block bg item.</p>
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class BlockTextPlotter : Plotter() {
  /**
   * Text.
   */
  var text: String? = null

  /**
   * Block style.
   */
  val blockTextStyle = BlockTextStyle()

  override fun measureMinWidth() = appendExpectedMinWidth(super.measureMinWidth(), blockTextStyle)

  override fun measureMinHeight() = appendExpectedMinHeight(super.measureMinHeight(), blockTextStyle)

  /* Draw */

  override fun onDraw(bound: Rect, canvas: Canvas, itemData: ItemData?) {
    // draw text inside rect bound.
    blockTextStyle.drawBlockWithText(bound, canvas, getBindString(itemData, unbindReplace = this.text))
  }
}