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
package com.ijoic.item_plotter.style.dimen

import android.graphics.Rect

/**
 * Round dimension.
 *
 * @author xiao.yl on 2018/1/22.
 * @version 1.0
 */
class RoundDimen {
  /**
   * Left value.
   */
  var left: Int = 0
    set(value) {
      field = value
      stateInit = true
    }

  /**
   * Top value.
   */
  var top: Int = 0
    set(value) {
      field = value
      stateInit = true
    }

  /**
   * Right value.
   */
  var right: Int = 0
    set(value) {
      field = value
      stateInit = true
    }

  /**
   * Bottom value.
   */
  var bottom: Int = 0
    set(value) {
      field = value
      stateInit = true
    }

  private var stateInit = false

  /**
   * Returns round dimen empty status(not set any dimen property yet).
   */
  val isEmpty: Boolean
    get() = !stateInit

  /**
   * Min required width.
   */
  val minRequiredWidth: Int
      get() = left + right

  /**
   * Min required height.
   */
  val minRequiredHeight: Int
      get() = top + bottom

  /**
   * Set property value.
   *
   * @param left left.
   * @param top top.
   * @param right right.
   * @param bottom bottom.
   */
  fun set(left: Int, top: Int, right: Int, bottom: Int) {
    this.left = left
    this.top = top
    this.right = right
    this.bottom = bottom
  }

  /**
   * Set horizontal property value.
   *
   * @param value value.
   */
  fun setHorizontal(value: Int) {
    this.left = value
    this.right = value
  }

  /**
   * Set vertical property value.
   *
   * @param value value.
   */
  fun setVertical(value: Int) {
    this.top = value
    this.bottom = value
  }

  /**
   * Set all property value.
   *
   * @param value value.
   */
  fun setAll(value: Int) {
    set(value, value, value, value)
  }

  /**
   * Trim bound.
   */
  fun trimBound(bound: Rect) {
    bound.apply {
      left += this@RoundDimen.left
      top += this@RoundDimen.left
      right -= this@RoundDimen.right
      bottom -= this@RoundDimen.bottom
    }
  }
}