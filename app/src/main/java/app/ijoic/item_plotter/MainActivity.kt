package app.ijoic.item_plotter

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import app.ijoic.item_plotter.entity.User
import app.ijoic.item_plotter.plotter.RoundRectPlotter
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.data.TransformItemData
import com.ijoic.item_plotter.plotter.TextPlotter
import com.ijoic.item_plotter.plotter_group.LinearPlotterGroup

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    testSimple()
  }

  private fun testSimple() {
    val itemView = findViewById<ItemView>(R.id.item_view)

    // Test plotter group.
    itemView.plotter = LinearPlotterGroup().apply {
      addPlotter(TextPlotter().apply {
        setBindKey("user_name")
        setPlotterId(R.id.user_name)
        setTouchEnabled(true)

        text = "Hello World"
        textStyle.apply {
          gravity = Gravity.CENTER
          textColor = 0xFF333333.toInt()
          textSize = 48F
        }
        backgroundStyle.apply {
          backgroundColor = 0xFFF5F5F5.toInt()
        }
      },
      LinearLayout.LayoutParams(
        0,
        ViewGroup.LayoutParams.MATCH_PARENT,
        1F
      ))

      addPlotter(RoundRectPlotter().apply {
        setBindKey("user_age")
        setPlotterId(R.id.test_hello_world)
        setTouchEnabled(true)

        text = "Hello World"
        textStyle.apply {
          padding.setAll(20)
          gravity = Gravity.END or Gravity.BOTTOM
          textColor = 0xFF333333.toInt()
          textSize = 48F
        }
        backgroundStyle.apply {
          padding.setAll(20)
          backgroundColor = 0xffd0d0d0.toInt()
        }

        rectStyle.apply {
          width = ViewGroup.LayoutParams.MATCH_PARENT
          height = ViewGroup.LayoutParams.MATCH_PARENT
          padding.setAll(20)
          backgroundColor = Color.GREEN
          radius = 20F
        }
      },
      LinearLayout.LayoutParams(
          0,
          ViewGroup.LayoutParams.MATCH_PARENT,
          1F
      ))
    }

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
          Toast.makeText(this@MainActivity, "${plotter.getBindKey()} clicked", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
