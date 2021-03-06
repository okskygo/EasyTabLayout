package com.rex.easytablayout.tab

import android.content.Context
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EasyTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
  : TabLayout(context, attrs) {

  fun <T : TabSource> setupWithViewPager(viewPager: ViewPager,
                                         fragmentManager: FragmentManager,
                                         factory: TabFactory<T>) {
    val tabPagerAdapter = TabPagerAdapter(fragmentManager, factory)
    viewPager.adapter = tabPagerAdapter
    super.setupWithViewPager(viewPager)
    addOnTabSelectedListener(EasyTabLayout.ViewPagerOnTabSelectedListener(viewPager,
        tabPagerAdapter))
    tabPagerAdapter.initCustomTabs(this)
  }

  private class ViewPagerOnTabSelectedListener<T : TabSource>(viewPager: ViewPager,
                                                              private val tabPagerAdapter: TabPagerAdapter<T>)
    : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
    override fun onTabSelected(tab: Tab?) {
      super.onTabSelected(tab)
      val position = tab?.position ?: return
      val factory = tabPagerAdapter.factory as? TTabFactory ?: return
      factory.onTabSelectedSubject.onNext(factory.available[position])
    }
  }
}

abstract class TabFactory<T : TabSource>(open val available: List<T>,
                                         open val bundle: Bundle? = null) {

  abstract fun onTabSelected(): Observable<T>

  class Builder {

    private var bundle: Bundle? = null

    fun addBundle(bundle: Bundle) {
      this.bundle = bundle
    }

    fun <T : TabSource> create(available: List<T>): TabFactory<T> {
      return TTabFactory(available, bundle)
    }
  }
}

private class TTabFactory<T : TabSource> constructor(override val available: List<T>,
                                                     override val bundle: Bundle? = null) : TabFactory<T>(
    available,
    bundle) {

  val onTabSelectedSubject: PublishSubject<T> = PublishSubject.create<T>()

  override fun onTabSelected(): Observable<T> = onTabSelectedSubject

}

sealed class TabSource {

  abstract class ImageTabSource : TabSource() {
    @get:DrawableRes
    abstract val icon: Int
  }

  abstract class TextTabSource : TabSource()

  @get:StringRes
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
