package com.ijoic.item_plotter.style

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.ijoic.item_plotter.util.StyleUtils
import com.ijoic.item_plotter.util.pool.RectPool

/**
 * Drawable style.
 *
 * @author xiao.yl on 2018/1/30.
 * @version 1.0
 */
class DrawableStyle: PlotterStyle() {

  /**
   * Draw drawable.
   *
   * @param bound bound.
   * @param canvas canvas.
   * @param drawable drawable.
   * @param renderAppend renderAppend: fun(drawBound: Rect).
   */
  fun drawDrawable(bound: Rect, canvas: Canvas, drawable: Drawable?, renderAppend: ((Rect) -> Unit)? = null) {
    val baseWidth = padding.left + padding.right
    val baseHeight = padding.top + padding.bottom

    if (drawable == null) {
      // always run render append, even if background is not required to render.
      if (renderAppend != null) {
        val blockRect = RectPool.obtain()
        StyleUtils.measureBlock(bound, this, baseWidth, baseHeight, blockRect)
        renderAppend.invoke(blockRect)
        RectPool.release(blockRect)
      }
      return
    }
    val drawableBound = RectPool.obtain()
    val width = drawable.intrinsicWidth
    val height = drawable.minimumHeight

    StyleUtils.measureBlock(bound, this, width, height, drawableBound)

    StyleUtils.drawAndClipBound(drawableBound, canvas, {
      val restoreCount = canvas.save()

      canvas.translate(drawableBound.left.toFloat(), drawableBound.top.toFloat())
      drawable.setBounds(0, 0, width, height)
      drawable.draw(canvas)

      canvas.restoreToCount(restoreCount)
      renderAppend?.invoke(drawableBound)
    })
    RectPool.release(drawableBound)
  }

}