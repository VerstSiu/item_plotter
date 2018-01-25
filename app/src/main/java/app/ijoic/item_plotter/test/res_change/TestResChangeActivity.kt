package app.ijoic.item_plotter.test.res_change

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.test.res_change.config.RenderConfig
import kotlinx.android.synthetic.main.act_test_res_change.*

/**
 * Test res change activity.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class TestResChangeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.act_test_res_change)
    initToggleAction()
  }

  private fun initToggleAction() {
    toggle_button.setOnClickListener {
      RenderConfig.themeRed = !RenderConfig.themeRed
      test_res_item.requestLayout()
    }
  }
}