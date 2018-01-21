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

import com.ijoic.item_plotter.util.LazyLoader
import com.ijoic.item_plotter.value.ValueReader

/**
 * Item data.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
interface ItemData {
  /**
   * String reader.
   */
  fun stringReader(): LazyLoader<ValueReader<String>>

  /**
   * Int reader.
   */
  fun intReader(): LazyLoader<ValueReader<Int>>

  /**
   * Long reader.
   */
  fun longReader(): LazyLoader<ValueReader<Long>>

  /**
   * Float reader.
   */
  fun floatReader(): LazyLoader<ValueReader<Float>>

  /**
   * Double reader.
   */
  fun doubleReader(): LazyLoader<ValueReader<Double>>

  /**
   * Boolean reader.
   */
  fun booleanReader(): LazyLoader<ValueReader<Boolean>>
}