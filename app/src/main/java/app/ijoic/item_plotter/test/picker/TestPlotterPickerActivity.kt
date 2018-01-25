package app.ijoic.item_plotter.test.picker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.common.entity.User
import app.ijoic.item_plotter.test.picker.adapter.TestPickerAdapter
import app.ijoic.item_plotter.test.picker.constants.RenderConfig
import kotlinx.android.synthetic.main.act_test_picker.*

/**
 * Test plotter picker activity.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class TestPlotterPickerActivity : AppCompatActivity() {

  private var adapter: TestPickerAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_test_picker)
    initToggleAction()
    initTestList()
  }

  private fun initToggleAction() {
    toggle_button.setOnClickListener {
      RenderConfig.displaySimple = !RenderConfig.displaySimple
      adapter?.notifyDataSetChanged()
    }
  }

  private fun initTestList() {
    val listView = findViewById<RecyclerView>(R.id.test_list)
    val adapter = TestPickerAdapter(this)
    this.adapter = adapter

    listView.apply {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
      addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    adapter.itemsData = listOf(
        User("Tony", 17),
        User("Jenny", 16),
        User("John", 16),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21),
        User("Smith", 21)
    )
    adapter.notifyDataSetChanged()
  }
}