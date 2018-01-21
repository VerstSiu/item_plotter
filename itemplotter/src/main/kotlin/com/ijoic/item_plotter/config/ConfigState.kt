package com.ijoic.item_plotter.config

/**
 * Config state.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class ConfigState<in VALUE>: StateItem {

  private var stateInit = false
  private var oldValue: VALUE? = null
  private var newValue: VALUE? = null

  /**
   * Set config value.
   */
  fun setValue(value: VALUE) {
    newValue = value
  }

  override fun isChanged(): Boolean {
    if (!stateInit) {
      return true
    }
    return oldValue != newValue
  }

  override fun upgradeValue() {
    oldValue = newValue
    stateInit = true
  }
}