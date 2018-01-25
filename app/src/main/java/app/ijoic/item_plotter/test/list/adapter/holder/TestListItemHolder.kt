package app.ijoic.item_plotter.test.list.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import app.ijoic.item_plotter.R

/**
 * Test list item holder.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class TestListItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
  /**
   * Test button.
   */
  val testButton = itemView.findViewById<Button>(R.id.test_button)
}