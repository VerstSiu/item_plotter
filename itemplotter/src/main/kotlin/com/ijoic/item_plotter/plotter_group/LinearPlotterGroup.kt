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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ijoic.item_plotter.PlotterGroup
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.util.MeasureUtils

/**
 * Linear plotter group.
 *
 * <p>Only width, height and weight of LinearLayout.LayoutParams was used right now.</p>
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
open class LinearPlotterGroup(orientation: Int = HORIZONTAL): PlotterGroup<LinearLayout.LayoutParams>() {

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

  override fun measure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.measure(widthMeasureSpec, heightMeasureSpec)

    if (isHorizontal) {
      performMeasureHorizontal(widthMeasureSpec, heightMeasureSpec)
    } else {
      performMeasureVertical(widthMeasureSpec, heightMeasureSpec)
    }
  }

  private fun performMeasureHorizontal(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val plotterItems = getPlotterItems()
    val requiredWidth = measuredWidth
    val requiredHeight = measuredHeight
    var totalWidth = 0
    var totalHeight = 0

    var weightTotal = 0F
    var childParams: LinearLayout.LayoutParams?
    var childWeight: Float
    var childParamWidth: Int?

    val childWidthMeasureSpec = MeasureUtils.exactly2atMost(widthMeasureSpec)
    val childHeightMeasureSpec = MeasureUtils.exactly2atMost(heightMeasureSpec)

    // measure child items which's weight is negative or zero.
    plotterItems.forEach {
      childParams = getChildLayoutParams(it)
      childParamWidth = childParams?.width

      if (childParamWidth == 0) {
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        weightTotal += childWeight
      } else {
        it.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        totalWidth += it.measuredWidth
        totalHeight = Math.max(totalHeight, it.measuredHeight)
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

          it.measure(childWidthMeasureSpec, childHeightMeasureSpec)
          it.setMeasureDimension(childWeightWidth, it.measuredHeight)
          totalHeight = Math.max(totalHeight, it.measuredHeight)
        }
      }
    }

    setExpectedMeasureDimension(
        widthMeasureSpec,
        heightMeasureSpec,
        Math.max(totalWidth, requiredWidth),
        Math.max(totalHeight, requiredHeight)
    )
  }

  private fun performMeasureVertical(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val plotterItems = getPlotterItems()
    val requiredWidth = measuredWidth
    val requiredHeight = measuredHeight
    var totalWidth = 0
    var totalHeight = 0

    var weightTotal = 0F
    var childParams: LinearLayout.LayoutParams?
    var childWeight: Float
    var childParamHeight: Int?

    val childWidthMeasureSpec = MeasureUtils.exactly2atMost(widthMeasureSpec)
    val childHeightMeasureSpec = MeasureUtils.exactly2atMost(heightMeasureSpec)

    // measure child items which's weight is negative or zero.
    plotterItems.forEach {
      childParams = getChildLayoutParams(it)
      childParamHeight = childParams?.height

      if (childParamHeight == 0) {
        childWeight = Math.max(childParams?.weight ?: 0F, 0F)
        weightTotal += childWeight
      } else {
        it.measure(childWidthMeasureSpec, childHeightMeasureSpec)
        totalHeight += it.measuredHeight
        totalWidth = Math.max(totalWidth, it.measuredWidth)
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

          it.measure(childWidthMeasureSpec, childHeightMeasureSpec)
          it.setMeasureDimension(it.measuredWidth, childWeightHeight)
          totalWidth = Math.max(totalWidth, it.measuredWidth)
        }
      }
    }

    setExpectedMeasureDimension(
        widthMeasureSpec,
        heightMeasureSpec,
        Math.max(totalWidth, requiredWidth),
        Math.max(totalHeight, requiredHeight)
    )
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
        drawLeft += it.measuredWidth
      }

    } else {
      var drawTop = top

      plotterItems.forEach {
        touchId = it.getTouchPlotterId(left, drawTop, touchX, touchY)

        if (touchId != 0) {
          return touchId
        }
        drawTop += it.measuredHeight
      }
    }
    return touchId
  }

  /* Draw */

  override fun onDraw(context: Context, bound: Rect, canvas: Canvas, itemData: ItemData?) {
    val left = bound.left
    val top = bound.top
    val plotterItems = getPlotterItems()

    if (isHorizontal) {
      var drawLeft = left

      plotterItems.forEach {
        it.draw(context, drawLeft, top, canvas, itemData)
        drawLeft += it.measuredWidth
      }

    } else {
      var drawTop = top

      plotterItems.forEach {
        it.draw(context, left, drawTop, canvas, itemData)
        drawTop += it.measuredHeight
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