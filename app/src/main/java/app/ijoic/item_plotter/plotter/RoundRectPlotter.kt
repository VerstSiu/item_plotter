package app.ijoic.item_plotter.plotter

import android.graphics.Canvas
import android.graphics.Rect
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.plotter.BasePlotter
import com.ijoic.item_plotter.plotter.TextPlotter
import com.ijoic.item_plotter.style.BlockStyle
import com.ijoic.item_plotter.style.TextStyle

/**
 * Round rect plotter.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
class RoundRectPlotter: BasePlotter() {

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
    super.onDraw(bound, canvas, itemData)
    rectStyle.drawRoundRect(bound, canvas)
    textStyle.drawText(bound, canvas, getBindString(itemData, unbindReplace = this.text))
  }
}