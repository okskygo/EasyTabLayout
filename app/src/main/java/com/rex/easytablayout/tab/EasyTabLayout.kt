package com.rex.easytablayout.tab

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class EasyTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
  : TabLayout(context, attrs) {

  fun <T : TabSource> setupWithViewPager(viewPager: ViewPager,
                                         fragmentManager: FragmentManager,
                                         factory: TabFactory<T>) {

    val tabPagerAdapter = TabPagerAdapter(fragmentManager, factory)
    viewPager.adapter = tabPagerAdapter
    super.setupWithViewPager(viewPager)
    addOnTabSelectedListener(ViewPagerOnTabSelectedListener(viewPager, tabPagerAdapter))
    tabPagerAdapter.initCustomTabs(this)

  }

  class ViewPagerOnTabSelectedListener<T : TabSource>(viewPager: ViewPager,
                                                      private val tabPagerAdapter: TabPagerAdapter<T>)
    : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

    override fun onTabSelected(tab: Tab?) {
      super.onTabSelected(tab)
      val position = tab?.position ?: return
      tabPagerAdapter.factory.apply { onTabSelectedSubject.onNext(available[position]) }
    }

  }
}
