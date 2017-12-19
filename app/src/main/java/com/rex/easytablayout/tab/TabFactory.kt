package com.rex.easytablayout.tab

import android.os.Bundle
import io.reactivex.subjects.PublishSubject


abstract class TabFactory<T : TabSource> {

  abstract val available: List<T>

  val onTabSelectedSubject: PublishSubject<T> = PublishSubject.create<T>()

  //optional
  var bundle: Bundle? = null

}

