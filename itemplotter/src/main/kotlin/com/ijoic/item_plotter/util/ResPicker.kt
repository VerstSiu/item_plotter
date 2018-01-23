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

import android.content.Context
import android.content.res.Resources
import android.support.annotation.*
import android.support.v4.content.res.ResourcesCompat

/**
 * Res picker.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
class ResPicker(context: Context) {
  private val r = context.resources

  /**
   * Returns pixel size.
   *
   * @param id res id.
   */
  fun pickPixelSize(@DimenRes id: Int) = r.getDimensionPixelSize(id)

  /**
   * Returns pixel size as float.
   *
   * @param id res id.
   */
  fun pickPixelSizeAsFloat(@DimenRes id: Int) = pickPixelSize(id).toFloat()

  /**
   * Returns color.
   *
   * @param id res id.
   * @param theme res theme.
   */
  fun pickColor(@ColorRes id: Int, theme: Resources.Theme? = null) = ResourcesCompat.getColor(r, id, theme)

  /**
   * Returns color state list.
   *
   * @param id res id.
   * @param theme res theme.
   */
  fun pickColorStateList(@ColorRes id: Int, theme: Resources.Theme? = null) = ResourcesCompat.getColorStateList(r, id, theme)

  /**
   * Returns drawable.
   *
   * @param id res id.
   * @param theme res theme.
   */
  fun pickDrawable(@DrawableRes id: Int, theme: Resources.Theme? = null) = ResourcesCompat.getDrawable(r, id, theme)

  /**
   * Returns string value.
   *
   * @param id res id.
   */
  fun pickString(@StringRes id: Int) = r.getString(id)

  /**
   * Returns string array.
   *
   * @param id res id.
   */
  fun pickStringArray(@ArrayRes id: Int) = r.getStringArray(id)

  /**
   * Returns boolean.
   *
   * @param id res id.
   */
  fun pickBoolean(@BoolRes id: Int) = r.getBoolean(id)

  /**
   * Returns int.
   *
   * @param id res id.
   */
  fun pickInt(@IntegerRes id: Int) = r.getInteger(id)

  /**
   * Returns int array.
   *
   * @param id res id.
   */
  fun pickIntArray(@ArrayRes id: Int) = r.getIntArray(id)

  /**
   * Returns format string.
   *
   * @param id res id.
   * @param formatArgs format arguments.
   */
  fun formatString(@StringRes id: Int, vararg formatArgs: Any) = r.getString(id, formatArgs)
}