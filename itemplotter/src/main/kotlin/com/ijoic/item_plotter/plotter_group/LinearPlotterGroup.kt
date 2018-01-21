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
package com.ijoic.item_plotter.plotter_group

import android.graphics.Canvas
import android.graphics.Rect
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ijoic.item_plotter.ItemData

/**
 * Linear plotter group.
 *
 * <p>Only width, height and weight of LinearLayout.LayoutParams was used right now.</p>
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class LinearPlotterGroup(orientation: Int = HORIZONTAL): BasePlotterGroup<LinearLayout.LayoutParams>() {

  /* Orientation */

  /**
   * Orientation horizontal status.
   */
  private val isHorizontal = orientation == HORIZONTAL

  /* Layout params */

  override fun transformLayoutParams(oldParams: ViewGroup.LayoutParams): LinearLayout.LayoutParams {
    if (oldParams is LinearLayout.LayoutParams) {
      return oldParams
    }
    return LinearLayout.LayoutParams(
        oldParams.width,
        oldParams.height
    )
  }

  /* Measure */

  override fun measure(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int) {
    if (isHorizontal) {
      performMeasureHorizontal(resChanged, widthMeasureSpec, heightMeasureSpec)
    } else {
      performMeasureVertical(resChanged, widthMeasureSpec, heightMeasureSpec)
    }
  }

  private fun performMeasureHorizontal(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val plotterItems = getPlotterItems()
    var totalWidth = 0
    var totalHeight = 0

    var weightTotal = 0F
    var childParams: LinearLayout.LayoutParams?
    var childWeight: Float
    var childParamWidth: Int?

    // measure child items which's weight is negative or zero.
    plotterItems.forEach {
      childParams = getChildLayoutParams(it)
      childParamWidth = childParams?.width

      if (childParamWidth == 0) {
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        weightTotal += childWeight
      } else {
        it.measure(resChanged, widthMeasureSpec, heightMeasureSpec)
        totalWidth += it.getMeasuredWidth()
        totalHeight = Math.max(totalHeight, it.getMeasuredHeight())
      }
    }

    val maxWidth = getMaxSpecifiedWidth(widthMeasureSpec)

    // measure weight child items.
    if (weightTotal > 0 && totalWidth < maxWidth) {
      val weightWidthTotal = maxWidth - totalWidth
      var childWeightWidth: Int

      plotterItems.forEach {
        childParams = getChildLayoutParams(it)
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        childParamWidth = childParams?.width

        if (childParamWidth == 0 && childWeight > 0) {
          childWeightWidth = (weightWidthTotal * childWeight / weightTotal).toInt()
          totalWidth += childWeightWidth

          it.measure(resChanged, widthMeasureSpec, heightMeasureSpec)
          it.setMeasureDimension(childWeightWidth, it.getMeasuredHeight())
          totalHeight = Math.max(totalHeight, it.getMeasuredHeight())
        }
      }
    }

    setExpectedMeasureDimension(widthMeasureSpec, heightMeasureSpec, totalWidth, totalHeight)
  }

  private fun performMeasureVertical(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val plotterItems = getPlotterItems()
    var totalWidth = 0
    var totalHeight = 0

    var weightTotal = 0F
    var childParams: LinearLayout.LayoutParams?
    var childWeight: Float
    var childParamHeight: Int?

    // measure child items which's weight is negative or zero.
    plotterItems.forEach {
      childParams = getChildLayoutParams(it)
      childParamHeight = childParams?.height

      if (childParamHeight == 0) {
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        weightTotal += childWeight
      } else {
        it.measure(resChanged, widthMeasureSpec, heightMeasureSpec)
        totalHeight += it.getMeasuredHeight()
        totalWidth = Math.max(totalWidth, it.getMeasuredWidth())
      }
    }

    val maxHeight = getMaxSpecifiedWidth(heightMeasureSpec)

    // measure weight child items.
    if (weightTotal > 0 && totalHeight < maxHeight) {
      val weightHeightTotal = maxHeight - totalHeight
      var childWeightHeight: Int

      plotterItems.forEach {
        childParams = getChildLayoutParams(it)
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        childParamHeight = childParams?.height

        if (childParamHeight == 0 && childWeight > 0) {
          childWeightHeight = (weightHeightTotal * childWeight / weightTotal).toInt()
          totalHeight += childWeightHeight

          it.measure(resChanged, widthMeasureSpec, heightMeasureSpec)
          it.setMeasureDimension(it.getMeasuredWidth(), childWeightHeight)
          totalWidth = Math.max(totalWidth, it.getMeasuredWidth())
        }
      }
    }

    setExpectedMeasureDimension(widthMeasureSpec, heightMeasureSpec, totalWidth, totalHeight)
  }

  /* Touch */

  override fun getChildTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int {
    val plotterItems = getPlotterItems()
    var touchId = 0

    if (isHorizontal) {
      var drawLeft = left

      plotterItems.forEach {
        touchId = it.getTouchPlotterId(drawLeft, top, touchX, touchY)

        if (touchId != 0) {
          return touchId
        }
        drawLeft += it.getMeasuredWidth()
      }

    } else {
      var drawTop = top

      plotterItems.forEach {
        touchId = it.getTouchPlotterId(left, drawTop, touchX, touchY)

        if (touchId != 0) {
          return touchId
        }
        drawTop += it.getMeasuredHeight()
      }
    }
    return touchId
  }

  /* Draw */

  override fun onDraw(bound: Rect, itemData: ItemData?, canvas: Canvas) {
    super.onDraw(bound, itemData, canvas)
    val left = bound.left
    val top = bound.top
    val plotterItems = getPlotterItems()

    if (isHorizontal) {
      var drawLeft = left

      plotterItems.forEach {
        it.draw(drawLeft, top, itemData, canvas)
        drawLeft += it.getMeasuredWidth()
      }

    } else {
      var drawTop = top

      plotterItems.forEach {
        it.draw(left, drawTop, itemData, canvas)
        drawTop += it.getMeasuredHeight()
      }
    }
  }

  companion object {
    /**
     * Orientation horizontal.
     */
    const val HORIZONTAL = 0

    /**
     * Orientation vertical.
     */
    const val VERTICAL = 1
  }
}