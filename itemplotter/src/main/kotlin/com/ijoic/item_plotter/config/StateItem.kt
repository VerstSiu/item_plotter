package com.ijoic.item_plotter.config

/**
 * State item.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
internal interface StateItem {
  /**
   * Returns state changed status.
   */
  fun isChanged(): Boolean

  /**
   * Upgrade state value.
   */
  fun upgradeValue()
}