package app.ijoic.item_plotter.plotter

import android.graphics.Canvas
import android.graphics.Rect
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.style.BlockStyle
import com.ijoic.item_plotter.style.TextStyle

/**
 * Round rect plotter.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
class RoundRectPlotter: Plotter() {

  /**
   * Text.
   */
  var text: String? = null

  /**
   * Text style.
   */
  val textStyle = TextStyle()

  /**
   * Rect style.
   */
  val rectStyle = BlockStyle()

  override fun onDraw(bound: Rect, canvas: Canvas, itemData: ItemData?) {
    // draw text inside rect bound.
    rectStyle.drawRoundRect(bound, canvas, renderAppend = {
      textStyle.drawText(it, canvas, getBindString(itemData, unbindReplace = this.text))
    })
  }
}