package com.ijoic.item_plotter.util

import android.content.Context
import android.content.res.Resources
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
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
   * Returns drawable.
   *
   * @param id res id.
   * @param theme res theme.
   */
  fun pickDrawable(@DrawableRes id: Int, theme: Resources.Theme? = null) = ResourcesCompat.getDrawable(r, id, theme)

  /**
   * Returns int.
   *
   * @param id res id.
   */
  fun pickInt(@IntegerRes id: Int) = r.getInteger(id)
}