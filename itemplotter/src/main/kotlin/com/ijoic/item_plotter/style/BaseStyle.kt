package com.ijoic.item_plotter.style

import android.view.Gravity
import com.ijoic.item_plotter.style.dimen.RoundDimen

/**
 * Base style.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
open class BaseStyle {
  /**
   * Gravity.
   */
  var gravity: Int = Gravity.CENTER

  /**
   * Margin.
   */
  val margin = RoundDimen()

  /**
   * Padding.
   */
  val padding = RoundDimen()

}