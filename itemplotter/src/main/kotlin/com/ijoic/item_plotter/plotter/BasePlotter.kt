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
package com.ijoic.item_plotter.plotter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import com.ijoic.item_plotter.ItemData
import com.ijoic.item_plotter.Plotter
import com.ijoic.item_plotter.util.RectPool
import com.ijoic.item_plotter.util.StyleUtils

/**
 * Base plotter.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
abstract class BasePlotter: Plotter {

  /* BindKey */

  private var bindKey: String? = null

  override fun setBindKey(bindKey: String?) {
    this.bindKey = bindKey
  }

  override fun getBindKey() = bindKey

  /**
   * Returns bind string.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindString(itemData: ItemData?, bindKey: String? = null, unbindReplace: String? = null): String? {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.stringReader().loadInstance().get(readKey)
  }

  /**
   * Returns bind int.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindInt(itemData: ItemData?, bindKey: String? = null, unbindReplace: Int = 0): Int {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.intReader().loadInstance().get(readKey) ?: unbindReplace
  }

  /**
   * Returns bind long.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindLong(itemData: ItemData?, bindKey: String? = null, unbindReplace: Long = 0): Long {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.longReader().loadInstance().get(readKey) ?: unbindReplace
  }

  /**
   * Returns bind float.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindFloat(itemData: ItemData?, bindKey: String? = null, unbindReplace: Float = 0F): Float {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.floatReader().loadInstance().get(readKey) ?: unbindReplace
  }

  /**
   * Returns bind double.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindDouble(itemData: ItemData?, bindKey: String? = null, unbindReplace: Double = 0.0): Double {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.doubleReader().loadInstance().get(readKey) ?: unbindReplace
  }

  /**
   * Returns bind boolean.
   *
   * @param itemData item data.
   * @param bindKey bind key.
   * @param unbindReplace unbind replace value for which itemData is null or bindKey not set.
   */
  protected fun getBindBoolean(itemData: ItemData?, bindKey: String? = null, unbindReplace: Boolean = false): Boolean {
    val readKey = bindKey ?: this.bindKey

    if (itemData == null || readKey == null) {
      return unbindReplace
    }
    return itemData.booleanReader().loadInstance().get(readKey) ?: unbindReplace
  }

  /* LayoutParams */

  private var params: ViewGroup.LayoutParams? = null

  override fun getLayoutParams() = params

  override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
    this.params = params
  }

  /**
   * Returns params width.
   *
   * @param defaultWidth default width.
   */
  protected fun getParamsWidth(defaultWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT) = params?.width ?: defaultWidth

  /**
   * Returns params height.
   *
   * @param defaultHeight default height.
   */
  protected fun getParamsHeight(defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT) = params?.height ?: defaultHeight

  /* Resources */

  private var firstInit = false

  override fun prepareResource(context: Context): Boolean {
    if (checkResChanged(context)) {
      onPrepareResource(context)
      return true
    }
    return false
  }

  override fun checkResChanged(context: Context) = !firstInit

  /**
   * Prepare resource when res changed.
   *
   * @param context context.
   */
  override fun onPrepareResource(context: Context) {
    firstInit = true
  }

  /* Measure */

  private var measuredWidth: Int = 0
  private var measuredHeight: Int = 0

  private var firstMeasure = true
  private var lastMeasureWidthSpec: Int = 0
  private var lastMeasureHeightSpec: Int = 0

  override fun setMeasureDimension(width: Int, height: Int) {
    firstMeasure = false
    measuredWidth = Math.max(width, 0)
    measuredHeight = Math.max(height, 0)
  }

  /* Measure */

  override fun measure(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int) {
    if (!resChanged && !isMeasureChanged(widthMeasureSpec, heightMeasureSpec)) {
      return
    }

    // measure width.
    val paramsWidth = getParamsWidth()

    val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
    val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

    val measuredWidth = when (widthMode) {
      View.MeasureSpec.EXACTLY -> widthSize
      View.MeasureSpec.AT_MOST -> if (paramsWidth == ViewGroup.LayoutParams.MATCH_PARENT) widthSize else Math.max(paramsWidth, 0)
      View.MeasureSpec.UNSPECIFIED -> Math.max(paramsWidth, 0)
      else -> 0
    }

    // measure height.
    val paramsHeight = getParamsHeight()

    val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
    val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

    val measuredHeight = when (heightMode) {
      View.MeasureSpec.EXACTLY -> heightSize
      View.MeasureSpec.AT_MOST -> if (paramsWidth == ViewGroup.LayoutParams.MATCH_PARENT) heightSize else Math.max(paramsHeight, 0)
      View.MeasureSpec.UNSPECIFIED -> Math.max(paramsHeight, 0)
      else -> 0
    }

    // update measure dimension.
    setMeasureDimension(measuredWidth, measuredHeight)
  }

  /**
   * Set expected measure dimension.
   *
   * <p>Set Rule: exact -> exact; at_most -> Math.min(at_most, expect); unspecified -> expect.</p>
   *
   * @param widthMeasureSpec width measure spec.
   * @param heightMeasureSpec height measure spec.
   * @param expectedWidth expected width.
   * @param expectedHeight expected height.
   */
  protected fun setExpectedMeasureDimension(widthMeasureSpec: Int, heightMeasureSpec: Int, expectedWidth: Int, expectedHeight: Int) {
    setMeasureDimension(
        calcMeasureSizeWithExpected(widthMeasureSpec, expectedWidth),
        calcMeasureSizeWithExpected(heightMeasureSpec, expectedHeight)
    )
  }

  /**
   * Returns max specified spec size.
   *
   * @param measureSpec measure spec.
   */
  protected fun getMaxSpecifiedWidth(measureSpec: Int): Int {
    val specMode = View.MeasureSpec.getMode(measureSpec)
    val specSize = View.MeasureSpec.getSize(measureSpec)

    return when(specMode) {
      View.MeasureSpec.EXACTLY, View.MeasureSpec.AT_MOST -> specSize
      View.MeasureSpec.UNSPECIFIED -> 0
      else -> 0
    }
  }

  /**
   * Calculate measure size with expected size.
   *
   * @param measureSpec measure spec.
   * @param expectedSize expected size.
   */
  private fun calcMeasureSizeWithExpected(measureSpec: Int, expectedSize: Int): Int {
    val specMode = View.MeasureSpec.getMode(measureSpec)
    val specSize = View.MeasureSpec.getSize(measureSpec)

    return when(specMode) {
      View.MeasureSpec.EXACTLY -> specSize
      View.MeasureSpec.AT_MOST -> Math.min(specSize, expectedSize)
      View.MeasureSpec.UNSPECIFIED -> expectedSize
      else -> expectedSize
    }
  }

  override fun isMeasureChanged(widthMeasureSpec: Int, heightMeasureSpec: Int): Boolean {
    val oldWidthSpec = lastMeasureWidthSpec
    val oldHeightSpec = lastMeasureHeightSpec

    if (firstMeasure) {
      return true
    }
    if (firstMeasure || oldWidthSpec != widthMeasureSpec || oldHeightSpec != heightMeasureSpec) {
      lastMeasureHeightSpec = widthMeasureSpec
      lastMeasureHeightSpec = heightMeasureSpec
      return true
    }
    return false
  }

  override fun getMeasuredWidth() = measuredWidth

  override fun getMeasuredHeight() = measuredHeight

  /* Touch */

  private var plotterId: Int = 0

  override fun setPlotterId(plotterId: Int) {
    this.plotterId = plotterId
  }

  override fun getPlotterId() = plotterId

  override fun getTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int {
    if (isTouchEnabled() && plotterId != 0
        && touchX >= left && touchX <= (left + getMeasuredWidth() - 1)
        && touchY >= top && touchY <= (top + getMeasuredHeight() - 1)) {
      return plotterId
    }
    return 0
  }

  /* TouchEnabled */

  private var touchEnabled = false

  override fun setTouchEnabled(enabled: Boolean) {
    touchEnabled = enabled
  }

  override fun isTouchEnabled() = touchEnabled

  /* FindChild */

  override fun getPlotterById(plotterId: Int): Plotter? {
    if (plotterId != 0 && plotterId == this.plotterId) {
      return this
    }
    return null
  }

  /* Draw */

  /**
   * Background color.
   */
  var backgroundColor: Int = Color.TRANSPARENT

  override fun draw(left: Int, top: Int, canvas: Canvas, itemData: ItemData?) {
    val width = getMeasuredWidth()
    val height = getMeasuredHeight()
    val bound = RectPool.obtain()
    bound.set(left, top, left + width, top + height)

    StyleUtils.drawAndClipBound(bound, canvas, {
      onDraw(bound, canvas, itemData)
    })
    RectPool.release(bound)
  }

  /**
   * Draw while width or height is not null.
   *
   * @param bound bound.
   * @param itemData item data.
   * @param canvas canvas.
   */
  protected open fun onDraw(bound: Rect, canvas: Canvas, itemData: ItemData?) {
    // draw background.
    if (backgroundColor != Color.TRANSPARENT) {
      canvas.drawColor(backgroundColor)
    }
  }

}