package com.caldremch.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Created by Leon on 2022/7/9
 */

inline fun <reified T : Activity> Context.startActivity(bundle: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    bundle?.let { intent.putExtras(it) }
    if (this !is Activity) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun Context.startActivity(clz: Class<*>, bundle: Bundle? = null) {
    val intent = Intent(this, clz)
    bundle?.let { intent.putExtras(it) }
    if (this !is Activity) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

