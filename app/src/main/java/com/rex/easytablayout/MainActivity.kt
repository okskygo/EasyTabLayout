package com.rex.easytablayout

import android.os.Bundle
import android.widget.Toast
import com.rex.easytablayout.tab.TabFactory
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {

  private val availableTabs = arrayListOf(FirstTabSource(),
      SecondTabSource(),
      ThirdTabSource(),
      FourthTabSource())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val tabFactory = TabFactory.Builder().create(availableTabs)
    tabLayout.setupWithViewPager(pager, supportFragmentManager, tabFactory)

    tabFactory.onTabSelected().compose(bindUntilEvent(ActivityEvent.DESTROY))
        .subscribe {
          /*
          when (it) {
            is FirstTabSource -> TODO()
            is SecondTabSource -> TODO()
            is ThirdTabSource -> TODO()
            is FourthTabSource -> TODO()
          }
          */
          Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
        }

  }
}


