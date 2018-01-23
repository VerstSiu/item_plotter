/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.item_plotter

import android.content.Context
import android.support.annotation.IdRes
import android.view.ViewGroup

/**
 * Base plotter group.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
abstract class PlotterGroup<PARAMS: ViewGroup.LayoutParams>: Plotter() {

  /* LayoutParams */

  /**
   * Returns child layout params.
   *
   * @param plotter child plotter.
   */
  protected fun getChildLayoutParams(plotter: Plotter): PARAMS? {
    val oldParams = plotter.layoutParams ?: return null
    return transformLayoutParams(oldParams)
  }

  /**
   * Transform layout params.
   *
   * @param oldParams old params.
   */
  protected abstract fun transformLayoutParams(oldParams: ViewGroup.LayoutParams): PARAMS

  /* PlotterItems */

  private val plotterItems = ArrayList<Plotter>()

  /**
   * Returns plotter items.
   */
  protected fun getPlotterItems(): List<Plotter> = plotterItems.toMutableList()

  /**
   * Add plotter.
   *
   * @param plotter plotter.
   */
  fun addPlotter(plotter: Plotter, layoutParams: PARAMS?) {
    val oldParams = plotter.layoutParams

    if (oldParams == null) {
      plotter.layoutParams = layoutParams
    } else {
      plotter.layoutParams = transformLayoutParams(oldParams)
    }
    plotterItems.add(plotter)
  }

  /* Resources */

  override fun prepareResource(context: Context): Boolean {
    var resChanged = super.prepareResource(context)
    val plotterItems = getPlotterItems()

    plotterItems.forEach {
      if (it.prepareResource(context)) {
        resChanged = true
      }
    }
    return resChanged
  }

  /* Touch */

  override fun getTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int {
    val childPlotterId = getChildTouchPlotterId(left, top, touchX, touchY)

    if (childPlotterId != 0) {
      return childPlotterId
    }
    return super.getTouchPlotterId(left, top, touchX, touchY)
  }

  /**
   * Returns child touch plotter id.
   */
  @IdRes
  protected abstract fun getChildTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int

  /* FindChild */

  override fun getPlotterById(plotterId: Int): Plotter? {
    var plotter = super.getPlotterById(plotterId)

    if (plotter == null && plotterId != 0) {
      val childItems = getPlotterItems()

      childItems.forEach {
        plotter = it.getPlotterById(plotterId)

        if (plotter != null) {
          return plotter
        }
      }
    }
    return plotter
  }

}