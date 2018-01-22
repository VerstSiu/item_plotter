package com.ijoic.item_plotter.style

import android.graphics.*
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
class BlockStyle: BaseStyle() {
  /**
   * Width.
   *
   * <p>Use ViewGroup.LayoutParams.MATCH_PARENT to specify match width.</p>
   */
  var width: Int = 0

  /**
   * Height.
   *
   * <p>Use ViewGroup.LayoutParams.MATCH_PARENT to specify match height.</p>
   */
  var height: Int = 0

  /**
   * Background radius.
   */
  var radius: Float = 0F

  /**
   * Background color.
   */
  var backgroundColor: Int = Color.TRANSPARENT

  /**
   * Draw contents with clip rect.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param renderItem render item: fun(blockBound: Rect).
   */
  fun drawWithClipRect(bound: Rect, canvas: Canvas, renderItem: (Rect) -> Unit) {
    val blockRect = RectPool.obtain()
    StyleUtils.measureBlockRect(bound, this, blockRect)

    StyleUtils.drawWithClipRect(blockRect, canvas, {
      renderItem.invoke(blockRect)
    })

    RectPool.release(blockRect)
  }

  /**
   * Draw contents with clip rect float.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param renderItem render item: fun(blockBound: RectF).
   */
  fun drawWithClipRectF(bound: Rect, canvas: Canvas, renderItem: (RectF) -> Unit) {
    val blockRect = RectPool.obtain()
    StyleUtils.measureBlockRect(bound, this, blockRect)

    StyleUtils.drawWithClipRect(blockRect, canvas, {
      val blockRectF = RectFPool.obtain().apply { set(blockRect) }
      renderItem.invoke(blockRectF)
      RectFPool.release(blockRectF)
    })

    RectPool.release(blockRect)
  }

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint text paint.
   */
  fun drawColor(bound: Rect, canvas: Canvas, paint: Paint? = null) {
    if (paint == null) {
      drawWithClipRect(bound, canvas, {
        canvas.drawColor(backgroundColor)
      })

    } else {
      paint.color = backgroundColor

      drawWithClipRect(bound, canvas, {
        canvas.drawRect(it, paint)
      })
    }
  }

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint text paint.
   */
  fun drawRoundRect(bound: Rect, canvas: Canvas, paint: Paint? = null) {
    val colorPaint = paint ?: PaintPool.obtainFillPaint()
    colorPaint.color = backgroundColor

    drawWithClipRectF(bound, canvas, {
      canvas.drawRoundRect(it, radius, radius, colorPaint)
    })
    if (paint == null) {
      PaintPool.release(colorPaint)
    }
  }
}