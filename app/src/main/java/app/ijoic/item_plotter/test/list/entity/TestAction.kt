package app.ijoic.item_plotter.test.list.entity

import android.support.annotation.StringRes

/**
 * Test action.
 *
 * @author xiao.yl on 2018/1/25.
 * @version 1.0
 */
data class TestAction(
    /**
     * Title res.
     */
    @StringRes
    val titleRes: Int,

    /**
     * Action class.
     */
    val actionClazz: Class<*>
)