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
import android.view.ViewGroup
import com.ijoic.item_plotter.util.PaintPool
import com.ijoic.item_plotter.util.RectFPool
import com.ijoic.item_plotter.util.RectPool
import com.ijoic.item_plotter.util.StyleUtils

/**
 * Block style.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
open class BlockStyle: PlotterStyle() {
  /**
   * Width.
   *
   * <p>Use ViewGroup.LayoutParams.MATCH_PARENT to specify match width.</p>
   */
  var width: Int = ViewGroup.LayoutParams.MATCH_PARENT

  /**
   * Height.
   *
   * <p>Use ViewGroup.LayoutParams.MATCH_PARENT to specify match height.</p>
   */
  var height: Int = ViewGroup.LayoutParams.MATCH_PARENT

  /**
   * Background radius.
   */
  var radius: Float = 0F

  /**
   * Background color.
   */
  var backgroundColor: Int = Color.TRANSPARENT

  /**
   * Draw background auto..
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint block paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawBackground(bound: Rect, canvas: Canvas, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    val blockRect = RectPool.obtain()

    StyleUtils.measureBlock(bound, this, blockRect)
    drawBackgroundDirect(blockRect, canvas, paint, renderAppend)

    RectPool.release(blockRect)
  }

  /**
   * Draw background direct..
   *
   * @param blockBound block bound.
   * @param canvas canvas.
   * @param paint block paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawBackgroundDirect(blockBound: Rect, canvas: Canvas, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    if (blockBound.isEmpty) {
      return
    }
    val backgroundColor = this.backgroundColor

    if (backgroundColor == Color.TRANSPARENT) {
      // always run render append, even if background is not required to render.
      if (renderAppend != null) {
        StyleUtils.drawAndClipBound(blockBound, canvas, {
          StyleUtils.drawAndClipPadding(blockBound, renderAppend, padding)
        })
      }
      return
    }
    val radius = this.radius

    if (radius <= 0F) {
      drawColor(blockBound, canvas, backgroundColor, paint, renderAppend)
    } else {
      drawRoundRect(blockBound, canvas, backgroundColor, radius, paint, renderAppend)
    }
  }

  /**
   * Draw color.
   *
   * @param blockBound block bound.
   * @param canvas canvas.
   * @param backgroundColor background color.
   * @param paint block paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  private fun drawColor(blockBound: Rect, canvas: Canvas, backgroundColor: Int, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    if (paint == null) {
      StyleUtils.drawAndClipBound(blockBound, canvas, {
        canvas.drawColor(backgroundColor)
        StyleUtils.drawAndClipPadding(blockBound, renderAppend, padding)
      })

    } else {
      paint.color = backgroundColor

      StyleUtils.drawAndClipBound(blockBound, canvas, {
        canvas.drawRect(blockBound, paint)
        StyleUtils.drawAndClipPadding(blockBound, renderAppend, padding)
      })
    }
  }

  /**
   * Draw round rect.
   *
   * @param blockBound block bound.
   * @param canvas canvas.
   * @param backgroundColor background color.
   * @param radius radius.
   * @param paint block paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  private fun drawRoundRect(blockBound: Rect, canvas: Canvas, backgroundColor: Int, radius: Float, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    val colorPaint = paint ?: PaintPool.obtainFillPaint()
    colorPaint.color = backgroundColor
    colorPaint.isDither = true
    colorPaint.isAntiAlias = true

    StyleUtils.drawAndClipBound(blockBound, canvas, {
      val blockBoundRectF = RectFPool.obtainCopy(blockBound)

      canvas.drawRoundRect(blockBoundRectF, radius, radius, colorPaint)
      StyleUtils.drawAndClipPadding(blockBound, renderAppend, padding)

      RectFPool.release(blockBoundRectF)
    })
    PaintPool.checkNullRelease(paint, colorPaint)
  }
}