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

import android.content.Context
import android.graphics.Canvas
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Item view.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class ItemView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

  /* Plotter */

  /**
   * Plotter provider.
   */
  var plotter: Plotter? = null

  /**
   * Plotter Provider.
   *
   * <p>Use to override and provide plotter instance when item view is attached to window.
   */
  var plotterProvider: (() -> Plotter?)? = null

  /**
   * Returns plotter instance or null if not any plotter instance could be read or created.
   *
   * @see plotter
   * @see plotterProvider
   */
  private fun readPlotterInstance(): Plotter? {
    return plotter ?: plotterProvider?.invoke()
  }

  /* ItemData */

  /**
   * Item data.
   */
  var itemData: ItemData? = null

  /**
   * Item data Provider.
   *
   * <p>Use to override and provide item data instance when item view is attached to window.
   */
  var itemDataProvider: (() -> ItemData?)? = null

  /**
   * Returns item data instance or null if not any item data instance could be read or created.
   *
   * @see itemData
   * @see itemDataProvider
   */
  private fun readItemDataInstance(): ItemData? {
    return itemData ?: itemDataProvider?.invoke()
  }

  /* ItemClickListener */

  /**
   * Item click listener interface.
   */
  interface OnItemClickListener {
    /**
     * On item click.
     *
     * @param itemView item view.
     * @param plotterId plotter id.
     */
    fun onItemClick(itemView: ItemView, @IdRes plotterId: Int)
  }

  /**
   * Item click listener.
   */
  var itemClickListener: OnItemClickListener? = null

  /* WindowAttach */

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    plotter = null
    plotterProvider = null
    itemData = null
    itemDataProvider = null
    itemClickListener = null
  }

  /* Measure */

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val plotter = readPlotterInstance()

    if (plotter != null) {
      val resChanged = plotter.prepareResource(context)
      plotter.measure(resChanged, widthMeasureSpec, heightMeasureSpec)
      setMeasuredDimension(plotter.measuredWidth, plotter.measuredHeight)
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }

  /* TouchEvent */

  private var pressedPlotterId = 0

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    if (event == null) {
      return super.onTouchEvent(event)
    }
    when (event.action) {
      MotionEvent.ACTION_DOWN -> {
        pressedPlotterId = readPressedPlotterId(event)
        return pressedPlotterId != 0 || super.onTouchEvent(event)
      }

      MotionEvent.ACTION_UP -> {
        val oldPressedPlotterId = pressedPlotterId
        val newPressedPlotterId = readPressedPlotterId(event)

        if (oldPressedPlotterId != 0 && oldPressedPlotterId == newPressedPlotterId) {
          itemClickListener?.onItemClick(this, newPressedPlotterId)
          return true
        }
        return super.onTouchEvent(event)
      }

      else -> return pressedPlotterId != 0 || super.onTouchEvent(event)
    }
  }

  private fun readPressedPlotterId(event: MotionEvent) = readPlotterInstance()?.getTouchPlotterId(0, 0, event.x.toInt(), event.y.toInt()) ?: 0

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    if (canvas != null) {
      readPlotterInstance()?.draw(0, 0, canvas, readItemDataInstance())
    }
  }
}