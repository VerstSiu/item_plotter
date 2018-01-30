package com.ijoic.item_plotter.plotter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.style.DrawableStyle
import com.ijoic.item_plotter.util.pool.RectPool

/**
 * Icon right text plotter.
 *
 * @author xiao.yl on 2018/1/30.
 * @version 1.0
 */
class IconRightTextPlotter: TextPlotter() {
  /**
   * Icon style.
   */
  val iconStyle = DrawableStyle()

  /**
   * Icon.
   */
  var icon: Drawable? = null

  override fun onDraw(context: Context, bound: Rect, canvas: Canvas, itemData: ItemData?) {
    // draw icon
    val textBound = RectPool.obtainCopy(bound)

    if (icon != null) {
      iconStyle.drawDrawable(bound, canvas, icon, {
        textBound.right = it.left
      })
    }

    // draw text
    super.onDraw(context, textBound, canvas, itemData)

    // resource release
    RectPool.release(textBound)
  }
}