package app.ijoic.item_plotter.test.list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.test.list.adapter.TestListAdapter
import app.ijoic.item_plotter.test.list.entity.TestAction
import app.ijoic.item_plotter.test.picker.TestPlotterPickerActivity

/**
 * Test list activity.
 */
class TestListActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_test_list)
    initTestList()
  }

  private fun initTestList() {
    val listView = findViewById<RecyclerView>(R.id.test_list)
    val adapter = TestListAdapter(this)

    listView.apply {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    adapter.itemsData = listOf(
        TestAction(R.string.test_case_plotter_picker, TestPlotterPickerActivity::class.java)
    )
    adapter.notifyDataSetChanged()
  }
}
