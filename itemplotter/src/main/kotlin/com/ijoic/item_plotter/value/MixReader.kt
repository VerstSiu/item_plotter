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

/**
 * Mix reader.
 *
 * <p>Mix with SimpleReader and TransformReader.</p>
 * <p>Do not bind the same key with SimpleReader and Transform reader at the same time. Or you may get display error.</p>
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class MixReader<out ITEM, VALUE>(provideItem: () -> ITEM?): ValueReader<VALUE> {

  /**
   * Simple reader.
   */
  val simple = SimpleReader<VALUE>()

  /**
   * Transform reader.
   */
  val transform = TransformReader<ITEM, VALUE>(provideItem)

  override fun get(bindKey: String): VALUE? {
    return simple.get(bindKey) ?: transform.get(bindKey)
  }
}