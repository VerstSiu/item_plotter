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
 * Transform reader.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class TransformReader<out ITEM, VALUE>(
    private val provideItem: () -> ITEM?): ValueReader<VALUE> {

  /* Transform */

  private val transformMap = ConcurrentHashMap<String, ((ITEM) -> VALUE?)>()

  override fun get(bindKey: String): VALUE? {
    val transform = transformMap[bindKey] ?: return null
    val item = provideItem() ?: return null

    return transform(item)
  }

  /**
   * Add transform.
   *
   * <p>New transform with the same bind key will replace the old transform.</p>
   *
   * @param bindKey bind key.
   * @param transform transform.
   */
  fun addTransform(bindKey: String, transform: (ITEM) -> VALUE?) {
    transformMap[bindKey] = transform
  }
}