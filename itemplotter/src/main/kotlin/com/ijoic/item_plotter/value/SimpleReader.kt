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
package com.ijoic.item_plotter.value

import java.util.concurrent.ConcurrentHashMap

/**
 * Simple value reader.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class SimpleReader<VALUE>: ValueReader<VALUE> {

  private val valueMap = ConcurrentHashMap<String, VALUE>()

  override fun get(bindKey: String): VALUE? = valueMap[bindKey]

  /**
   * Put value.
   *
   * @param bindKey bind key.
   * @param value value.
   */
  fun putValue(bindKey: String, value: VALUE?) {
    if (value == null) {
      valueMap.remove(bindKey)
    } else {
      valueMap[bindKey] = value
    }
  }
}