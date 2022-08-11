package com.caldremch.android.common

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caldremch.common.ext.BaseActivity

class MainActivity2 : BaseActivity() {

    override val statusBarColorRes: Int?
        get() = android.R.color.white

    override val titleBarTitle: String
        get() = "super.titleBarTitle"

    override val titleColor: Int?
        get() = Color.RED

    override val layoutId: Int
        get() = R.layout.activity_main2
}