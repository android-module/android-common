package com.caldremch.android.common.sample

import android.app.Application
import com.caldremch.android.log.DebugLogInitializer

/**
 * Created by Leon on 2022/8/20.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DebugLogInitializer.init(true, "ws://192.168.101.2:8080/websocket")
    }
}