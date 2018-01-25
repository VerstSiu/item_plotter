package app.ijoic.item_plotter.common.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

/**
 * Base simple adapter.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
abstract class BaseSimpleAdapter<VH: RecyclerView.ViewHolder, ITEM>(context: Context): RecyclerView.Adapter<VH>() {
  protected val inflater = LayoutInflater.from(context)

  /**
   * Items data.
   */
  var itemsData: List<ITEM>? = null

  override fun getItemCount() = itemsData?.size ?: 0

}