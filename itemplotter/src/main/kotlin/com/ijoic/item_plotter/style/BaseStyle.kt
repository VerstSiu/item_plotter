package com.ijoic.item_plotter.style

import android.view.Gravity

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
   * Offset x.
   *
   * <p>Used only when gravity is not CENTER_HORIZONTAL.</p>
   */
  var offsetX: Int = 0

  /**
   * Offset y.
   *
   * <p>Used only when gravity is not CENTER_VERTICAL.</p>
   */
  var offsetY: Int = 0
}