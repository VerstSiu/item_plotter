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
package com.ijoic.item_plotter.util.pool

import android.support.v4.util.Pools

/**
 * Instance pool.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class InstancePool<T> internal constructor(
    private val createInstance: () -> T,
    maxSize: Int = 100) {

  private val innerPool = Pools.SynchronizedPool<T>(maxSize)

  /**
   * Obtain item instance.
   */
  fun obtain(): T = innerPool.acquire() ?: createInstance()

  /**
   * Release item instance.
   */
  fun release(instance: T) {
    onReleaseElement(instance)
    innerPool.release(instance)
  }

  /**
   * Check release.
   */
  fun checkRelease(required: Boolean, instance: T) {
    if (required) {
      release(instance)
    }
  }

  /**
   * Release instance if check object is null.
   */
  fun checkNullRelease(obj: Any?, instance: T) {
    if (obj == null) {
      release(instance)
    }
  }

  /**
   * Release element.
   *
   * @param instance instance.
   */
  protected open fun onReleaseElement(instance: T) {
  }
}