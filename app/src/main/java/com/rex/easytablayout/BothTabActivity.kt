package com.rex.easytablayout

import android.os.Bundle
import com.rex.easytablayout.tab.TabFactory
import com.rex.easytablayout.tab.TabSource
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_both_tab.*

class BothTabActivity : RxAppCompatActivity() {

  private val bottomTabs = arrayListOf(BottomFirstTabSource(),
      BottomSecondTabSource(),
      BottomThirdTabSource(), BottomFourthTabSource())

  private val firstTabs = arrayListOf(FirstTabSource(),
      SecondTabSource(),
      ThirdTabSource(),
      FourthTabSource())

  private val secondTabs = arrayListOf(FirstTabSource(),
      SecondTabSource(),
      ThirdTabSource())

  private val thirdTabs = arrayListOf(FirstTabSource(),
      SecondTabSource())

  private val fourthTabs = arrayListOf(FirstTabSource())

  private val tabFactory = TabFactory.Builder().create(bottomTabs)
  private val topTabFactory1 = TabFactory.Builder().create(firstTabs)
  private val topTabFactory2 = TabFactory.Builder().create(secondTabs)
  private val topTabFactory3 = TabFactory.Builder().create(thirdTabs)
  private val topTabFactory4 = TabFactory.Builder().create(fourthTabs)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_both_tab)

    tabFactory.onTabSelected().compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe {
          when (it) {
            is BottomFirstTabSource -> topTabLayout.setupWithViewPager(pager,
                supportFragmentManager,
                topTabFactory1)
            is BottomSecondTabSource -> topTabLayout.setupWithViewPager(pager,
                supportFragmentManager,
                topTabFactory2)
            is BottomThirdTabSource -> topTabLayout.setupWithViewPager(pager,
                supportFragmentManager,
                topTabFactory3)
            is BottomFourthTabSource -> topTabLayout.setupWithViewPager(pager,
                supportFragmentManager,
                topTabFactory4)
          }
        }
    bottomTabLayout.setup(tabFactory)
  }

  override fun onStart() {
    super.onStart()
    selectBottomTab()
  }

  private fun selectBottomTab() {
    bottomTabLayout.getTabAt(0)?.select()
    topTabLayout.setupWithViewPager(pager,
        supportFragmentManager,
        topTabFactory1)
  }
}

sealed class BottomTabSource : TabSource.ImageTabSource() {
  override val fragmentClass = null
}

class BottomFirstTabSource : BottomTabSource() {
  override val icon: Int = R.drawable.first
  override val title: Int = R.string.first
}

class BottomSecondTabSource : BottomTabSource() {
  override val icon: Int = R.drawable.second
  override val title: Int = R.string.second
}

class BottomThirdTabSource : BottomTabSource() {
  override val icon: Int = R.drawable.third
  override val title: Int = R.string.third
}

class BottomFourthTabSource : BottomTabSource() {
  override val icon: Int = R.drawable.fourth
  override val title: Int = R.string.fourth
  override val fragmentClass = null
}