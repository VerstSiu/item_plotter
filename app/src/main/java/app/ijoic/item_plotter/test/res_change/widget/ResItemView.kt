package app.ijoic.item_plotter.test.res_change.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import app.ijoic.item_plotter.R
import app.ijoic.item_plotter.common.constants.BindKey
import app.ijoic.item_plotter.common.entity.User
import app.ijoic.item_plotter.test.res_change.config.RenderConfig
import app.ijoic.item_plotter.test.res_change.res.SkinResManager
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.data.MixItemData
import com.ijoic.item_plotter.plotter.TextPlotter

/**
 * Res item view.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class ResItemView(context: Context, attrs: AttributeSet? = null): ItemView(context, attrs) {

  init {
    plotter = TextPlotter().apply {

      textStyle.apply {
        gravity = Gravity.CENTER
        textColor = 0xFF8BC34A.toInt()
        textSize = 48F
      }
      backgroundStyle.apply {
        padding.setAll(20)
        backgroundColor = 0xffeeeeee.toInt()
      }
      text = context.resources.getString(R.string.app_name)

      resManager = SkinResManager {
        val isThemeRed = RenderConfig.themeRed

        textStyle.apply {
          textColor = if (isThemeRed) 0xFFf44336.toInt()  else 0xFF8BC34A.toInt()
        }
      }
    }
  }

  /**
   * Data impl.
   */
  val dataImpl = MixItemData<User>().apply {
    stringReaderImpl.loadInstance().transform.addTransform(BindKey.USERNAME, { it.name })
    stringReaderImpl.loadInstance().transform.addTransform(BindKey.USER_AGE, { it.age.toString() })
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    itemData = dataImpl
  }

}