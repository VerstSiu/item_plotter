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
import android.support.annotation.IntRange
import android.view.ViewGroup

/**
 * Plotter.
 *
 * <p>Only support measure of MATCH_PARENT or exactly dimensions.</p>
 *
 * @author xiao.yl on 2018/1/20.
 * @version 1.0
 */
interface Plotter {

  /* BindKey */

  /**
   * Set bind key.
   *
   * @param bindKey bind key.
   */
  fun setBindKey(bindKey: String?)

  /**
   * Returns bind key.
   */
  fun getBindKey(): String?

  /* LayoutParams */

  /**
   * Returns layout params.
   */
  fun getLayoutParams(): ViewGroup.LayoutParams?

  /**
   * Set layout params.
   *
   * @param params layout params.
   */
  fun setLayoutParams(params: ViewGroup.LayoutParams?)

  /* Resource */

  /**
   * Check resources changed.
   *
   * @param context context.
   */
  fun checkResChanged(context: Context): Boolean

  /**
   * Prepare resource.
   *
   * @param context context.
   */
  fun prepareResource(context: Context): Boolean

  /**
   * Prepare resource when res changed.
   *
   * @param context context.
   */
  fun onPrepareResource(context: Context)

  /* Measure */

  /**
   * Measure.
   *
   * @param resChanged resources changed status.
   * @param widthMeasureSpec width measure spec.
   * @param heightMeasureSpec height measure spec.
   */
  fun measure(resChanged: Boolean, widthMeasureSpec: Int, heightMeasureSpec: Int)

  /**
   * Returns measure changed status.
   *
   * <p>Do use this method pre when measure start, and use only once.</p>
   * <p>Secondary called during the same measure scheduler will immediately return true if first init was completed.</p>
   *
   * @param widthMeasureSpec width measure spec.
   * @param heightMeasureSpec height measure spec.
   */
  fun isMeasureChanged(widthMeasureSpec: Int, heightMeasureSpec: Int): Boolean

  /**
   * Set measure dimension.
   *
   * @param width width.
   * @param height height.
   */
  fun setMeasureDimension(width: Int, height: Int)

  /**
   * Returns measured with.
   */
  @IntRange(from = 0L)
  fun getMeasuredWidth(): Int

  /**
   * Returns measured height.
   */
  @IntRange(from = 0L)
  fun getMeasuredHeight(): Int

  /* Touch */

  /**
   * Returns plotter id or zero if id is not set.
   */
  @IdRes
  fun getPlotterId(): Int

  /**
   * Set plotter id.
   *
   * @param plotterId plotter id.
   */
  fun setPlotterId(@IdRes plotterId: Int)

  /**
   * Returns touch plotter id or zero if not touch plotter item not found.
   *
   * @param left view left.
   * @param top view top.
   * @param touchX position x.
   * @param touchY position y.
   */
  @IdRes
  fun getTouchPlotterId(left: Int, top: Int, touchX: Int, touchY: Int): Int

  /**
   * Set touch enabled.
   *
   * @param enabled touch enabled.
   */
  fun setTouchEnabled(enabled: Boolean)

  /**
   * Returns touch enabled.
   */
  fun isTouchEnabled(): Boolean

  /* FindChild */

  /**
   * Returns id bind child generation plotter if exist.
   *
   * @param plotterId plotter id.
   */
  fun getPlotterById(@IdRes plotterId: Int): Plotter?

  /* Draw */

  /**
   * Draw plotter content.
   *
   * @param left left position.
   * @param top top position.
   * @param canvas canvas.
   * @param itemData item data.
   */
  fun draw(left: Int, top: Int, canvas: Canvas, itemData: ItemData?)
}