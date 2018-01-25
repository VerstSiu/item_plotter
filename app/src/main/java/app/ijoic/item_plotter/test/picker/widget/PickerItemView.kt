package app.ijoic.item_plotter.test.picker.widget

import android.content.Context
import android.util.AttributeSet
import app.ijoic.item_plotter.common.constants.BindKey
import app.ijoic.item_plotter.common.entity.User
import app.ijoic.item_plotter.common.widget.ItemPlotterSource
import app.ijoic.item_plotter.test.picker.constants.RenderConfig
import com.ijoic.item_plotter.ItemView
import com.ijoic.item_plotter.data.MixItemData

/**
 * Picker item view.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class PickerItemView(context: Context, attrs: AttributeSet? = null): ItemView(context, attrs) {

  /**
   * Item simple.
   */
  private val itemSimple = ItemPlotterSource.itemUsername.getPlotter(context)

  /**
   * Item rich.
   */
  private val itemRich = ItemPlotterSource.itemRankUsernameAge.getPlotter(context)

  init {
    plotter = itemSimple
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

  /**
   * Toggle plotter.
   */
  fun togglePlotter() {
    plotter = if (RenderConfig.displaySimple) itemSimple else itemRich
  }

}