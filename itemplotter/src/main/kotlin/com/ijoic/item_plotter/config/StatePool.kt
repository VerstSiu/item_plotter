package com.ijoic.item_plotter.config

/**
 * State pool.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class StatePool: StateItem {

  private val stateList = ArrayList<StateItem>()

  /**
   * Returns new config state.
   */
  fun<VALUE> newConfigState(defaultValue: VALUE): ConfigState<VALUE> {
    val stateItem = ConfigState<VALUE>(defaultValue)
    stateList.add(stateItem)
    return stateItem
  }

  override fun isChanged(): Boolean {
    stateList.toMutableList().forEach {
      if (it.isChanged()) {
        return true
      }
    }
    return false
  }

  override fun upgradeValue() {
    stateList.toMutableList().forEach {
      if (it.isChanged()) {
        it.upgradeValue()
      }
    }
  }

  override fun checkUpgrade(): Boolean {
    val changed = isChanged()
    upgradeValue()
    return changed
  }
}