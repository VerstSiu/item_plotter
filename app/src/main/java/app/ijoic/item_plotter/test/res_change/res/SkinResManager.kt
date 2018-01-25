package app.ijoic.item_plotter.test.res_change.res

import android.content.Context
import app.ijoic.item_plotter.test.res_change.config.RenderConfig
import com.ijoic.item_plotter.ResManager
import com.ijoic.item_plotter.config.ConfigState

/**
 * Skin res manager.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
class SkinResManager(private val resInitializer: (Context) -> Unit): ResManager {

  private val skinState = ConfigState(false)

  override fun checkResChanged(context: Context) = skinState.checkUpgrade(RenderConfig.themeRed)

  override fun prepareResource(context: Context) {
    resInitializer.invoke(context)
  }
}