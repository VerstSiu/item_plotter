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

import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.source.PlotterPicker
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Plotter picker extension.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */

/**
 * Bind plotter.
 *
 * <p>Bind plotter instance to property. And add plotter as local items.</p>
 *
 * @param plotter plotter.
 */
fun PlotterPicker.bindPlotter(plotter: Plotter) = object: ReadOnlyProperty<PlotterPicker, Plotter> {
  override fun getValue(thisRef: PlotterPicker, property: KProperty<*>): Plotter {
    thisRef.addPlotter(plotter)
    return plotter
  }
}