/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.item_plotter.config

/**
 * State pool.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class StatePool: StateItem {

  private val stateList = ArrayList<StateItem>()

  /**
   * Returns new config state.
   */
  fun<VALUE> newConfigState(defaultValue: VALUE): ConfigState<VALUE> {
    val stateItem = ConfigState<VALUE>(defaultValue)
    stateList.add(stateItem)
    return stateItem
  }

  override fun isChanged(): Boolean {
    stateList.toMutableList().forEach {
      if (it.isChanged()) {
        return true
      }
    }
    return false
  }

  override fun upgradeValue() {
    stateList.toMutableList().forEach {
      if (it.isChanged()) {
        it.upgradeValue()
      }
    }
  }

  override fun checkUpgrade(): Boolean {
    val changed = isChanged()
    upgradeValue()
    return changed
  }
}