package app.ijoic.item_plotter.test.picker.adapter

import android.content.Context
import android.view.ViewGroup
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.common.adapter.BaseSimpleAdapter
import app.ijoic.item_plotter.common.adapter.holder.SimpleChildHolder
import app.ijoic.item_plotter.common.constants.BindKey
import app.ijoic.item_plotter.common.entity.User
import app.ijoic.item_plotter.test.picker.widget.PickerItemView

/**
 * Test picker adapter.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class TestPickerAdapter(context: Context): BaseSimpleAdapter<SimpleChildHolder<PickerItemView>, User>(context) {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimpleChildHolder<PickerItemView> {
    val itemView = inflater.inflate(R.layout.item_test_picker, parent, false)
    return SimpleChildHolder(itemView, R.id.test_picker_item)
  }

  override fun onBindViewHolder(holder: SimpleChildHolder<PickerItemView>?, position: Int) {
    if (holder == null) {
      return
    }
    val user = itemsData?.getOrNull(position) ?: return

    holder.childView.dataImpl.apply {
      stringReaderImpl.loadInstance().simple.putValue(BindKey.RANK_INDEX, (position + 1).toString())
      item = user
    }
    holder.childView.togglePlotter()
    holder.childView.invalidate()
  }
}