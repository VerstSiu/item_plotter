package app.ijoic.item_plotter.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.adapter.holder.SimpleChildHolder
import app.ijoic.item_plotter.entity.User
import app.ijoic.item_plotter.widget.UserItemView

/**
 * Test adapter.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
class TestAdapter(context: Context): RecyclerView.Adapter<SimpleChildHolder<UserItemView>>() {
  private val inflater = LayoutInflater.from(context)

  /**
   * Items data.
   */
  var itemsData: List<User>? = null

  override fun getItemCount() = itemsData?.size ?: 0

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimpleChildHolder<UserItemView> {
    val itemView = inflater.inflate(R.layout.item_plotter_test, parent, false)
    return SimpleChildHolder(itemView, R.id.test_user_item)
  }

  override fun onBindViewHolder(holder: SimpleChildHolder<UserItemView>?, position: Int) {
    if (holder == null) {
      return
    }
    holder.childView.dataImpl.apply {
      stringReaderImpl.loadInstance().simple.putValue("user_index", (position + 10001).toString())
      item = itemsData?.elementAtOrNull(position)
    }
    holder.childView.invalidate()
  }

}