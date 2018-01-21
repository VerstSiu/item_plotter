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
package com.ijoic.item_plotter.data

import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.util.LazyLoader
import com.ijoic.item_plotter.value.SimpleReader

/**
 * Simple item data.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
class SimpleItemData: ItemData {

  val stringReaderImpl = createLazySimpleReader<String>()
  val intReaderImpl = createLazySimpleReader<Int>()
  val longReaderImpl = createLazySimpleReader<Long>()
  val floatReaderImpl = createLazySimpleReader<Float>()
  val doubleReaderImpl = createLazySimpleReader<Double>()
  val booleanReaderImpl = createLazySimpleReader<Boolean>()

  override fun stringReader() = stringReaderImpl

  override fun intReader() = intReaderImpl

  override fun longReader() = longReaderImpl

  override fun floatReader() = floatReaderImpl

  override fun doubleReader() = doubleReaderImpl

  override fun booleanReader() = booleanReaderImpl

  companion object {
    /**
     * Returns lazy simple reader instance.
     */
    private fun<T> createLazySimpleReader(): LazyLoader<SimpleReader<T>> {
      return LazyLoader<SimpleReader<T>>({ SimpleReader<T>() })
    }
  }

}