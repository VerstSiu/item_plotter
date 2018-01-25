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
package com.ijoic.item_plotter.source.loader

import android.content.Context
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.source.PlotterLoader
import java.lang.ref.WeakReference

/**
 * Weak plotter loader.
 *
 * <p>Use weak reference to keep plotter cache instance.</p>
 * <p>Plotter instance will be release if it was not kept by other objects any more.</p>
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class WeakPlotterLoader(
    private val loader: (Context) -> Plotter): PlotterLoader {

  private val lock = Object()
  private var refCachePlotter: WeakReference<Plotter>? = null

  override fun getPlotter(context: Context): Plotter {
    synchronized(lock) {
      val oldPlotter = refCachePlotter?.get()
      val plotter = oldPlotter ?: loader.invoke(context)

      if (oldPlotter == null) {
        refCachePlotter = WeakReference(plotter)
      }
      return plotter
    }
  }
}