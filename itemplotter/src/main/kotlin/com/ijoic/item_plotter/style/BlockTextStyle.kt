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
import com.ijoic.item_plotter.util.pool.PaintPool
import com.ijoic.item_plotter.util.pool.RectPool
import com.ijoic.item_plotter.util.StyleUtils

/**
 * Block text style.
 *
 * @author xiao.yl on 2018/1/24.
 * @version 1.0
 */
class BlockTextStyle : BlockStyle() {

  /**
   * Text style.
   */
  val textStyle = TextStyle()

  override fun measureMinWidth() = super.measureMinWidth() + textStyle.measureMinWidth()

  override fun measureMinHeight() = super.measureMinHeight() + textStyle.measureMinHeight()

  /**
   * Draw block with text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param text text.
   * @param bgPaint background paint.
   * @param textPaint text paint.
   * @param blockRenderAppend block render append: fun(textBound: Rect).
   * @param textRenderAppend text render append: fun(textBound: Rect).
   * @param clipPadding clip padding content or not.
   */
  fun drawBlockWithText(bound: Rect, canvas: Canvas, text: String?, bgPaint: Paint? = null, textPaint: Paint? = null,
                        blockRenderAppend: ((Rect) -> Unit)? = null, textRenderAppend: ((Rect) -> Unit)? = null, clipPadding: Boolean = false) {
    // draw steps:
    // 1. measure text bound.
    // 2. measure block bound.
    // 3. adjust block bound with text bound.
    // 4. draw block.
    // 5. draw text.
    val textBound = RectPool.obtain()
    val blockBound = RectPool.obtain()
    val drawBgPaint = bgPaint ?: PaintPool.obtainSmoothFillPaint()
    val drawTextPaint = textPaint ?: PaintPool.obtainSmoothFillPaint()

    // measure
    textStyle.measureTextBound(text, textBound, drawTextPaint)
    StyleUtils.measureBlock(bound, this, blockBound)

    // adjust
    adjustBlockBound(bound, textBound, blockBound)

    // draw block
    drawBackgroundDirect(blockBound, canvas, renderAppend = {
      textStyle.drawTextWithTextBound(it, canvas, text, textBound, drawTextPaint, textRenderAppend, clipPadding)
      blockRenderAppend?.invoke(it)
    })

    // resource release
    RectPool.release(textBound)
    RectPool.release(blockBound)
    PaintPool.checkNullRelease(bgPaint, drawBgPaint)
    PaintPool.checkNullRelease(textPaint, drawTextPaint)
  }

  /**
   * Adjust block bound.
   *
   * @param bound bound.
   * @param textBound text bound.
   * @param blockBound block bound.
   */
  private fun adjustBlockBound(bound: Rect, textBound: Rect, blockBound: Rect) {
    if (textBound.isEmpty) {
      return
    }
    val minRequiredTextWidth = textBound.width() + padding.minRequiredWidth
    val minRequiredTextHeight = textBound.height() + padding.minRequiredHeight
    val blockWidth = blockBound.width()
    val blockHeight = blockBound.height()

    if (minRequiredTextWidth > blockWidth) {
      StyleUtils.extendBlockHorizontal(blockBound, gravity, minRequiredTextWidth - blockWidth)
      StyleUtils.trimBlockHorizontal(bound, blockBound)
    }

    if (minRequiredTextHeight > blockHeight) {
      StyleUtils.extendBlockVertical(blockBound, gravity, minRequiredTextHeight - blockHeight)
      StyleUtils.trimBlockVertical(bound, blockBound)
    }
  }
}