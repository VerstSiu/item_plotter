package com.ijoic.item_plotter.util

import android.graphics.Path

/**
 * Path pool.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
object PathPool : InstancePool<Path>({ Path() }) {
  override fun onReleaseElement(instance: Path) {
    instance.reset()
  }
}