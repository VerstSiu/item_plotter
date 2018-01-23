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
  fun drawAndClipRect(bound: Rect, canvas: Canvas, renderItem: (Rect) -> Unit) {
    val blockRect = RectPool.obtain()
    StyleUtils.measureBlock(bound, this, blockRect)

    StyleUtils.drawAndClipBound(blockRect, canvas, {
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
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawAndClipRectF(bound: Rect, canvas: Canvas, renderItem: (RectF) -> Unit, renderAppend: ((Rect) -> Unit)? = null) {
    drawAndClipRect(bound, canvas, {
      val blockRectF = RectFPool.obtain().apply { set(it) }
      renderItem.invoke(blockRectF)
      renderAppend?.invoke(it)
      RectFPool.release(blockRectF)
    })
  }

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint text paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawColor(bound: Rect, canvas: Canvas, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    val backgroundColor = this.backgroundColor

    if (backgroundColor == Color.TRANSPARENT) {
      // always run render append, even if background is not required to render.
      if (renderAppend != null) {
        drawAndClipRect(bound, canvas, {
          StyleUtils.drawAndClipPadding(it, renderAppend, padding)
        })
      }
      return
    }

    // draw color
    if (paint == null) {
      drawAndClipRect(bound, canvas, {
        canvas.drawColor(backgroundColor)
        StyleUtils.drawAndClipPadding(it, renderAppend, padding)
      })

    } else {
      paint.color = backgroundColor

      drawAndClipRect(bound, canvas, {
        canvas.drawRect(it, paint)
        StyleUtils.drawAndClipPadding(it, renderAppend, padding)
      })
    }
  }

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint text paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawRoundRect(bound: Rect, canvas: Canvas, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    val backgroundColor = this.backgroundColor

    if (backgroundColor == Color.TRANSPARENT) {
      // always run render append, even if background is not required to render.
      if (renderAppend != null) {
        drawAndClipRect(bound, canvas, {
          StyleUtils.drawAndClipPadding(it, renderAppend, padding)
        })
      }
      return
    }

    // draw round rect
    val colorPaint = paint ?: PaintPool.obtainFillPaint()
    colorPaint.color = backgroundColor

    drawAndClipRectF(bound, canvas, {
      canvas.drawRoundRect(it, radius, radius, colorPaint)
    }, renderAppend = {
      StyleUtils.drawAndClipPadding(it, renderAppend, padding)
    })

    if (paint == null) {
      PaintPool.release(colorPaint)
    }
  }

  /**
   * Draw text.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param paint text paint.
   * @param renderAppend render item: fun(blockBound: Rect).
   */
  fun drawBackgroundAuto(bound: Rect, canvas: Canvas, paint: Paint? = null, renderAppend: ((Rect) -> Unit)? = null) {
    if (radius == 0F) {
      drawColor(bound, canvas, paint, renderAppend)
    } else {
      drawRoundRect(bound, canvas, paint, renderAppend)
    }
  }
}