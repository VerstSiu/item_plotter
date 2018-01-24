package app.ijoic.item_plotter.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import app.ijoic.item_plotter.entity.User
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.data.MixItemData
import com.ijoic.item_plotter.plotter.BlockTextPlotter
import com.ijoic.item_plotter.plotter.TextPlotter
import com.ijoic.item_plotter.plotter_group.LinearPlotterGroup

/**
 * User item view.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
class UserItemView(context: Context, attrs: AttributeSet? = null): ItemView(context, attrs) {

  /**
   * Data impl.
   */
  val dataImpl = MixItemData<User>().apply {
    stringReaderImpl.loadInstance().transform.addTransform("user_name", { it.name })
    stringReaderImpl.loadInstance().transform.addTransform("user_age", { it.age.toString() })
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    itemData = dataImpl
    plotter = plotterImpl
  }

  companion object {
    /**
     * Plotter impl.
     */
    private val plotterImpl = LinearPlotterGroup().apply {
      addPlotter(
          BlockTextPlotter().apply {
            bindKey = "user_index"

            backgroundStyle.apply {
              padding.setAll(20)
              backgroundColor = 0xFFF5F5F5.toInt()
            }

            blockTextStyle.apply {
              width = 96
              height = 96
              gravity = Gravity.CENTER
              backgroundColor = 0xFFFF9800.toInt()
              radius = 20F

              textStyle.apply {
                gravity = Gravity.CENTER
                textColor = Color.WHITE
                textSize = 48F
              }
            }
          },
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              1F
          )
      )

      addPlotter(
          TextPlotter().apply {
            bindKey = "user_name"

            textStyle.apply {
              gravity = Gravity.START or Gravity.CENTER_VERTICAL
              textColor = 0xFF333333.toInt()
              textSize = 48F
              margin.left = 20
            }
            backgroundStyle.apply {
              padding.setAll(20)
              backgroundColor = 0xffeeeeee.toInt()
            }
          },
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              2F
          )
      )

      addPlotter(
          TextPlotter().apply {
            bindKey = "user_age"

            textStyle.apply {
              gravity = Gravity.START or Gravity.CENTER_VERTICAL
              textColor = 0xFF333333.toInt()
              textSize = 48F
              margin.left = 20
            }
            backgroundStyle.apply {
              padding.setAll(20)
              backgroundColor = 0xffeeeeee.toInt()
            }
          },
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              2F
          )
      )

    }
  }
}