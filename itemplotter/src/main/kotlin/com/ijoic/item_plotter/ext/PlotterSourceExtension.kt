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
package com.ijoic.item_plotter.ext

import android.content.Context
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.source.PlotterLoader
import com.ijoic.item_plotter.source.PlotterSource
import com.ijoic.item_plotter.source.loader.SimplePlotterLoader
import com.ijoic.item_plotter.source.loader.WeakPlotterLoader
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Plotter source extension.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */

/**
 * Bind plotter.
 *
 * <p>This method will return simple plotter loader instance.
 *
 * @param loader plotter loader.
 *
 * @see SimplePlotterLoader
 */
fun PlotterSource.bindPlotter(loader: (Context) -> Plotter) = object: ReadOnlyProperty<PlotterSource, PlotterLoader> {
  override fun getValue(thisRef: PlotterSource, property: KProperty<*>) = SimplePlotterLoader(loader)
}

/**
 * Bind plotter weak.
 *
 * <p>This method will return weak plotter loader instance.
 *
 * @param loader plotter loader.
 *
 * @see WeakPlotterLoader
 */
fun PlotterSource.bindPlotterWeak(loader: (Context) -> Plotter) = object: ReadOnlyProperty<PlotterSource, PlotterLoader> {
  override fun getValue(thisRef: PlotterSource, property: KProperty<*>) = WeakPlotterLoader(loader)
}