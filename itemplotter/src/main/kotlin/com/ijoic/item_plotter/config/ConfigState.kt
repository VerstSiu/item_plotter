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
 * Config state.
 *
 * @author xiao.yl on 2018/1/21.
 * @version 1.0
 */
class ConfigState<VALUE>(
    private val defaultValue: VALUE): StateItem {

  private var stateInit = false
  private var oldValue: VALUE? = null
  private var newValue: VALUE? = null

  /**
   * Set config value.
   *
   * @param value config value.
   */
  fun setValue(value: VALUE) {
    newValue = value
  }

  /**
   * Returns config change state, and upgrade config to new value.
   *
   * @param value config value.
   */
  fun checkUpgrade(value: VALUE): Boolean {
    setValue(value)
    return checkUpgrade()
  }

  /**
   * Returns current config value, or default value if config value is not found.
   */
  fun getValue(): VALUE {
    return newValue ?: defaultValue
  }

  override fun isChanged(): Boolean {
    if (!stateInit) {
      return true
    }
    return oldValue != newValue
  }

  override fun upgradeValue() {
    oldValue = newValue
    stateInit = true
  }

  override fun checkUpgrade(): Boolean {
    val changed = isChanged()
    upgradeValue()
    return changed
  }
}