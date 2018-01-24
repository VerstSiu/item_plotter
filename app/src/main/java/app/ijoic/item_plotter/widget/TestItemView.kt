package app.ijoic.item_plotter.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import app.ijoic.item_plotter.R
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.plotter.BlockTextPlotter
import com.ijoic.item_plotter.plotter.TextPlotter
import com.ijoic.item_plotter.plotter_group.LinearPlotterGroup

/**
 * Test item view.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
class TestItemView(context: Context, attrs: AttributeSet? = null): ItemView(context, attrs) {
  init {
    plotter = LinearPlotterGroup().apply {
      addPlotter(
          TextPlotter().apply {
            bindKey = "user_name"
            plotterId = R.id.user_name
            isTouchEnabled = true

            text = "Hello World"
            textStyle.apply {
              gravity = Gravity.CENTER
              textColor = 0xFF333333.toInt()
              textSize = 48F
            }
            backgroundStyle.apply {
              backgroundColor = 0xFFF5F5F5.toInt()
            }
          },
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              1F
          )
      )

      addPlotter(
          BlockTextPlotter().apply {
            bindKey = "user_age"
            plotterId = R.id.test_hello_world
            isTouchEnabled = true

            text = "Hello World"
            backgroundStyle.apply {
              padding.setAll(20)
              backgroundColor = 0xffd0d0d0.toInt()
            }

            blockTextStyle.apply {
              width = ViewGroup.LayoutParams.MATCH_PARENT
              height = ViewGroup.LayoutParams.MATCH_PARENT
              padding.setAll(20)
              backgroundColor = Color.GREEN
              radius = 20F

              textStyle.apply {
                padding.setAll(20)
                gravity = Gravity.END or Gravity.BOTTOM
                textColor = 0xFF333333.toInt()
                textSize = 48F
              }
            }
          },
          LinearLayout.LayoutParams(
              300,
              ViewGroup.LayoutParams.MATCH_PARENT
          )
      )
    }
  }
}