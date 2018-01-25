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
import android.graphics.Rect
import android.support.annotation.IdRes
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import com.ijoic.item_plotter.style.BlockStyle
import com.ijoic.item_plotter.style.PlotterStyle
import com.ijoic.item_plotter.util.pool.RectPool

/**
 * Plotter.
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
abstract class Plotter {

  /* BindKey */

  /**
   * Bind key.
   */
  var bindKey: String? = null

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

  /**
   * Layout params.
   */
  var layoutParams: ViewGroup.LayoutParams? = null

  /**
   * Min width.
   *
   * <p>Supports value for ViewGroup.LayoutParams.MATCH_PARENT.</p>
   */
  var minWidth = 0

  /**
   * Min height.
   *
   * <p>Supports value for ViewGroup.LayoutParams.MATCH_PARENT.</p>
   */
  var minHeight = 0

  /**
   * Returns params width.
   *
   * @param defaultWidth default width.
   */
  protected fun getParamsWidth(defaultWidth: Int = ViewGroup.LayoutParams.WRAP_CONTENT) = layoutParams?.width ?: defaultWidth

  /**
   * Returns params height.
   *
   * @param defaultHeight default height.
   */
  protected fun getParamsHeight(defaultHeight: Int = ViewGroup.LayoutParams.WRAP_CONTENT) = layoutParams?.height ?: defaultHeight

  /* Resources */

  private var firstInit = false

  /**
   * Resources manager.
   */
  var resManager: ResManager? = null

  /**
   * Prepare resource.
   *
   * <p>Returns true if config changed or first init not started.</p>
   *
   * @param context context.
   */
  internal open fun prepareResource(context: Context): Boolean {
    val oldFirstInit = firstInit
    val resManager = this.resManager
    firstInit = true

    if (resManager != null && resManager.checkResChanged(context)) {
      resManager.prepareResource(context)
      return true
    }
    return !oldFirstInit
  }

  /* Measure */

  private var innerMeasuredWidth: Int = 0
  private var innerMeasuredHeight: Int = 0

  private var firstMeasure = true
  private var lastMeasureWidthSpec: Int = 0
  private var lastMeasureHeightSpec: Int = 0

  /**
   * Measured width.
   */
  val measuredWidth: Int
    @IntRange(from = 0L) get() = innerMeasuredWidth

  /**
   * Measured height.
   */
  val measuredHeight: Int
    @IntRange(from = 0L) get() = innerMeasuredHeight

  /**
   * Measure.
   *
   * @param resChanged resources changed status.
   * @param widthMeasureSpec width measure spec.
   * @param heightMeasureSpec height measure spec.
   */
  open fun measure(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int) {
    if (!resChanged && !isMeasureChanged(widthMeasureSpec, heightMeasureSpec)) {
      return
    }

    // measure width.
    val paramsWidth = getParamsWidth()
    val realParamsWidth = Math.max(paramsWidth, 0)
    val requiredWidth = Math.max(realParamsWidth, measureMinWidth())

    val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
    val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)

    val measuredWidth = when (widthMode) {
      View.MeasureSpec.EXACTLY -> widthSize
      View.MeasureSpec.AT_MOST -> when {
        paramsWidth == ViewGroup.LayoutParams.MATCH_PARENT -> widthSize
        minWidth == ViewGroup.LayoutParams.MATCH_PARENT -> widthSize
        else -> Math.min(widthSize, requiredWidth)
      }
      View.MeasureSpec.UNSPECIFIED -> requiredWidth
      else -> 0
    }

    // measure height.
    val paramsHeight = getParamsHeight()
    val realParamsHeight = Math.max(paramsHeight, 0)
    val requiredHeight = Math.max(realParamsHeight, measureMinHeight())

    val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
    val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

    val measuredHeight = when (heightMode) {
      View.MeasureSpec.EXACTLY -> heightSize
      View.MeasureSpec.AT_MOST -> when {
        paramsHeight == ViewGroup.LayoutParams.MATCH_PARENT -> heightSize
        minHeight == ViewGroup.LayoutParams.MATCH_PARENT -> heightSize
        else -> Math.min(heightSize, requiredHeight)
      }
      View.MeasureSpec.UNSPECIFIED -> requiredHeight
      else -> 0
    }

    // update measure dimension.
    setMeasureDimension(measuredWidth, measuredHeight)
  }

  /**
   * Returns measured min width.
   */
  protected open fun measureMinWidth() = Math.max(backgroundStyle.measureMinWidth(), minWidth)

  /**
   * Returns measured min height.
   */
  protected open fun measureMinHeight() = Math.max(backgroundStyle.measureMinHeight(), minHeight)

  /**
   * Append expected min width.
   *
   * @param baseWidth base width.
   * @param styles styles.
   */
  protected fun appendExpectedMinWidth(baseWidth: Int, vararg styles: PlotterStyle): Int {
    var appendWidth = baseWidth

    styles.forEach {
      appendWidth += it.measureMinWidth()
    }
    return appendWidth
  }

  /**
   * Append expected min height.
   *
   * @param baseHeight base height.
   * @param styles styles.
   */
  protected fun appendExpectedMinHeight(baseHeight: Int, vararg styles: PlotterStyle): Int {
    var appendHeight = baseHeight

    styles.forEach {
      appendHeight += it.measureMinHeight()
    }
    return appendHeight
  }

  /**
   * Returns measure changed status.
   *
   * <p>Do use this method pre when measure start, and use only once.</p>
   * <p>Secondary called during the same measure scheduler will immediately return true if first init was completed.</p>
   *
   * @param widthMeasureSpec width measure spec.
   * @param heightMeasureSpec height measure spec.
   */
  protected open fun isMeasureChanged(widthMeasureSpec: Int, heightMeasureSpec: Int): Boolean {
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

  /**
   * Set measure dimension.
   *
   * @param width width.
   * @param height height.
   */
  fun setMeasureDimension(width: Int, height: Int) {
    firstMeasure = false
    innerMeasuredWidth = Math.max(width, 0)
    innerMeasuredHeight = Math.max(height, 0)
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

  /* Touch */

  /**
   * Plotter id.
   */
  @IdRes
  var plotterId: Int = 0

  /**
   * Touch enabled status.
   */
  var isTouchEnabled = false

  /**
   * Returns touch plotter id or zero if not touch plotter item not found.
   *
   * @param left view left.
   * @param top view top.
   * @param touchX position x.
   * @param touchY position y.
   */
  @IdRes
  open fun getTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int {
    if (isTouchEnabled && plotterId != 0
        && touchX >= left && touchX <= (left + measuredWidth - 1)
        && touchY >= top && touchY <= (top + measuredHeight - 1)) {
      return plotterId
    }
    return 0
  }

  /* FindChild */

  /**
   * Returns id bind child generation plotter if exist.
   *
   * @param plotterId plotter id.
   */
  open fun getPlotterById(plotterId: Int): Plotter? {
    if (plotterId != 0 && plotterId == this.plotterId) {
      return this
    }
    return null
  }

  /* Draw */

  /**
   * Background style.
   */
  val backgroundStyle = BlockStyle()

  /**
   * Draw plotter content.
   *
   * @param left left position.
   * @param top top position.
   * @param canvas canvas.
   * @param itemData item data.
   */
  fun draw(left: Int, top: Int, canvas: Canvas, itemData: ItemData?) {
    val width = measuredWidth
    val height = measuredHeight
    val bound = RectPool.obtain()
    bound.set(left, top, left + width, top + height)

    backgroundStyle.drawBackground(bound, canvas, renderAppend = {
      onDraw(it, canvas, itemData)
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
  protected abstract fun onDraw(bound: Rect, canvas: Canvas, itemData: ItemData?)

}