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
import com.ijoic.item_plotter.value.MixReader

/**
 * Mix item data.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class MixItemData<ITEM>: ItemData {
  private val providerItem: () -> ITEM? = { item }

  /**
   * Item.
   */
  var item: ITEM? = null

  val stringReaderImpl = createLazyMixReader<ITEM, String>(providerItem)
  val intReaderImpl = createLazyMixReader<ITEM, Int>(providerItem)
  val longReaderImpl = createLazyMixReader<ITEM, Long>(providerItem)
  val floatReaderImpl = createLazyMixReader<ITEM, Float>(providerItem)
  val doubleReaderImpl = createLazyMixReader<ITEM, Double>(providerItem)
  val booleanReaderImpl = createLazyMixReader<ITEM, Boolean>(providerItem)

  override fun stringReader() = stringReaderImpl

  override fun intReader() = intReaderImpl

  override fun longReader() = longReaderImpl

  override fun floatReader() = floatReaderImpl

  override fun doubleReader() = doubleReaderImpl

  override fun booleanReader() = booleanReaderImpl

  companion object {
    /**
     * Returns lazy simple reader instance.
     *
     * @param providerItem provider item.
     */
    private fun<ITEM, T> createLazyMixReader(providerItem: () -> ITEM?): LazyLoader<MixReader<ITEM, T>> {
      return LazyLoader<MixReader<ITEM, T>>({ MixReader<ITEM, T>(providerItem) })
    }
  }

}