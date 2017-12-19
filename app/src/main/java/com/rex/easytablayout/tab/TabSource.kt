package com.rex.easytablayout.tab

import android.support.v4.app.Fragment

sealed class TabSource {

  abstract class ImageTabSource : TabSource() {
    abstract val icon: Int
  }

  abstract class TextTabSource : TabSource()

  abstract val title: Int
  abstract protected val fragmentClass: Class<out Fragment>

  val fragmentInstance: Fragment? by lazy {
    try {
      fragmentClass.newInstance()
    } catch (e: InstantiationException) {
      null
    } catch (e: IllegalAccessException) {
      null
    }
  }
}