package com.rex.easytablayout

import android.support.v4.app.Fragment
import com.rex.easytablayout.tab.TabSource

sealed class MainTabSource : TabSource.TextTabSource()

class FirstTabSource : MainTabSource() {
  //  override val icon: Int = R.drawable.second
  override val title: Int = R.string.first
  override val fragmentClass: Class<out Fragment> = EmptyFragment::class.java
}

class SecondTabSource : MainTabSource() {
  //  override val icon: Int = R.drawable.second
  override val title: Int = R.string.second
  override val fragmentClass: Class<out Fragment> = EmptyFragment::class.java
}

class ThirdTabSource : MainTabSource() {
  //  override val icon: Int = R.drawable.third
  override val title: Int = R.string.third
  override val fragmentClass: Class<out Fragment> = EmptyFragment::class.java
}

class FourthTabSource : MainTabSource() {
  //  override val icon: Int = R.drawable.fourth
  override val title: Int = R.string.fourth
  override val fragmentClass: Class<out Fragment> = EmptyFragment::class.java
}