package app.ijoic.item_plotter.test.list.adapter

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.test.list.adapter.holder.TestListItemHolder
import app.ijoic.item_plotter.common.adapter.BaseSimpleAdapter
import app.ijoic.item_plotter.test.list.entity.TestAction

/**
 * Test adapter.
 *
 * @author xiao.yl on 2018/1/23.
 * @version 1.0
 */
class TestListAdapter(context: Context): BaseSimpleAdapter<TestListItemHolder, TestAction>(context) {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TestListItemHolder {
    val itemView = inflater.inflate(R.layout.item_test_list, parent, false)
    return TestListItemHolder(itemView)
  }

  override fun onBindViewHolder(holder: TestListItemHolder?, position: Int) {
    if (holder == null) {
      return
    }
    val testAction = itemsData?.getOrNull(position) ?: return

    holder.testButton.apply {
      text = context.getString(testAction.titleRes)
      setOnClickListener {
        context.startActivity(Intent(context, testAction.actionClazz))
      }
    }
  }

}