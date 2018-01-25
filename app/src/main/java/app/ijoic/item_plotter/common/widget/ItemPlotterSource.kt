package app.ijoic.item_plotter.common.widget

import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import app.ijoic.item_plotter.common.constants.BindKey
import com.ijoic.item_plotter.ext.bindPlotterWeak
import com.ijoic.item_plotter.plotter.BlockTextPlotter
import com.ijoic.item_plotter.plotter.TextPlotter
import com.ijoic.item_plotter.plotter_group.LinearPlotterGroup
import com.ijoic.item_plotter.source.PlotterSource

/**
 * Item plotter source.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
object ItemPlotterSource: PlotterSource {

  /**
   * Item username.
   */
  val itemUsername by bindPlotterWeak {
    username.getPlotter(it)
  }

  /**
   * Item rank user name age.
   */
  val itemRankUsernameAge by bindPlotterWeak {
    LinearPlotterGroup().apply {
      addPlotter(
          rankTitle.getPlotter(it),
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              1F
          )
      )

      addPlotter(
          username.getPlotter(it),
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              2F
          )
      )

      addPlotter(
          age.getPlotter(it),
          LinearLayout.LayoutParams(
              0,
              ViewGroup.LayoutParams.MATCH_PARENT,
              2F
          )
      )

    }
  }

  /**
   * Rank title.
   */
  private val rankTitle by bindPlotterWeak {
    BlockTextPlotter().apply {
      bindKey = BindKey.RANK_INDEX
      text = "-"

      backgroundStyle.apply {
        padding.setAll(20)
        backgroundColor = 0xFFF5F5F5.toInt()
      }

      blockTextStyle.apply {
        width = 96
        height = 96
        gravity = Gravity.CENTER
        padding.setHorizontal(20)
        backgroundColor = 0xFFFF9800.toInt()
        radius = 20F

        textStyle.apply {
          padding.bottom = 10
          padding.right = 10
//                gravity = Gravity.CENTER
          gravity = Gravity.END or Gravity.BOTTOM
          textColor = Color.WHITE
          textSize = 48F
        }
      }
    }
  }

  /**
   * Username.
   */
  private val username by bindPlotterWeak {
    TextPlotter().apply {
      bindKey = BindKey.USERNAME

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
    }
  }

  /**
   * Age.
   */
  private val age by bindPlotterWeak {
    TextPlotter().apply {
      bindKey = BindKey.USER_AGE

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
    }
  }
}