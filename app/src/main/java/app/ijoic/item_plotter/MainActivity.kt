package app.ijoic.item_plotter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import app.ijoic.item_plotter.adapter.TestAdapter
import app.ijoic.item_plotter.entity.User
import app.ijoic.item_plotter.widget.TestItemView
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.data.TransformItemData

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    testSimple()
    testList()
  }

  private fun testSimple() {
    val itemView = findViewById<TestItemView>(R.id.item_view)

    // Test bind data.
    itemView.postDelayed({
      val itemData = TransformItemData<User>()
      val user = User("Jenny")
      itemData.stringReaderImpl.loadInstance().apply {
        addTransform("user_name", { it.name })
        addTransform("user_age", { it.age.toString() })
      }
      itemView.itemData = itemData

      itemData.item = user
      itemView.invalidate()
    }, 2000)

    // Test touch event.
    itemView.itemClickListener = object: ItemView.OnItemClickListener {
      override fun onItemClick(itemView: ItemView, plotterId: Int) {
        val plotter = itemView.plotter?.getPlotterById(plotterId)

        if (plotter != null) {
          Toast.makeText(this@MainActivity, "${plotter.bindKey} clicked", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun testList() {
    val listView = findViewById<RecyclerView>(R.id.test_list)
    val adapter = TestAdapter(this)

    listView.apply {
      this.adapter = adapter
      layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
      addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
    }

    listView.postDelayed({
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
    }, 4000)
  }
}
