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
import com.ijoic.item_plotter.value.MixReader
import com.ijoic.item_plotter.value.TransformReader

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

  /**
   * String reader impl.
   */
  val stringReaderImpl = MixReader<ITEM, String>(providerItem)

  override fun stringReader() = stringReaderImpl

}