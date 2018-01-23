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