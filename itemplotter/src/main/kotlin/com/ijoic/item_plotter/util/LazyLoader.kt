package com.ijoic.item_plotter.util

/**
 * Lazy loader.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
class LazyLoader<out VALUE>(private val createInstance: () -> VALUE) {
  private var instance: VALUE? = null

  /**
   * Returns lazy instance.
   */
  fun loadInstance(): VALUE {
    val oldInstance = this.instance

    if (oldInstance != null) {
      return oldInstance
    }
    val newInstance = createInstance()
    instance = newInstance
    return newInstance
  }
}