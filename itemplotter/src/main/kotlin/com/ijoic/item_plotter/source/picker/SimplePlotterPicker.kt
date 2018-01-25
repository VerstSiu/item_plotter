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
package com.ijoic.item_plotter.source.picker

import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.source.PlotterPicker

/**
 * Simple plotter picker.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
open class SimplePlotterPicker: PlotterPicker {

  private val itemsLock = Object()
  private val items = ArrayList<Plotter>()

  /**
   * Pick item: fun(): Plotter.
   */
  var pickItem: (() -> Plotter)? = null

  override fun addPlotter(plotter: Plotter) {
    synchronized(itemsLock) {
      items.add(plotter)
    }
  }

  override fun getPlotterItems(): List<Plotter> {
    synchronized(itemsLock) {
      return items.toMutableList()
    }
  }

  override fun getDisplayPlotter() = pickItem?.invoke()

}