package app.ijoic.item_plotter.common.adapter.holder

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Simple child holder.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
open class SimpleChildHolder<out T: View>(itemView: View, @IdRes childId: Int): RecyclerView.ViewHolder(itemView) {
  /**
   * Child view.
   */
  val childView = itemView.findViewById<T>(childId)
}