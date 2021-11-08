package com.sun.sunhotfix

import android.app.Application

/**
 * @author hengyangji
 * on 2021/11/8
 */
class SunApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        HotFix.doHotFix(this)
    }
}