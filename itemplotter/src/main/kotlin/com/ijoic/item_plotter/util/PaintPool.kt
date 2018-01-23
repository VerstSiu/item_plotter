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
package com.ijoic.item_plotter.util

import android.graphics.Paint

/**
 * RectF pool.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
object PaintPool : InstancePool<Paint>({ Paint() }) {
  override fun onReleaseElement(instance: Paint) {
    instance.reset()
  }

  /**
   * Returns fill paint.
   */
  fun obtainFillPaint() = obtain().apply {
    style = Paint.Style.FILL
  }

  /**
   * Returns smooth fill paint.
   */
  fun obtainSmoothFillPaint() = obtainFillPaint().apply {
    isDither = true
    isAntiAlias = true
  }

  /**
   * Returns stroke paint.
   */
  fun obtainStrokePaint() = obtain().apply {
    style = Paint.Style.STROKE
  }

  /**
   * Returns smooth stroke paint.
   */
  fun obtainSmoothStrokePaint() = obtainStrokePaint().apply {
    isDither = true
    isAntiAlias = true
  }
}