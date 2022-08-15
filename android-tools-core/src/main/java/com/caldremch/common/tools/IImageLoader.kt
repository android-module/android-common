package com.caldremch.common.tools

import android.content.Context
import android.widget.ImageView
import java.io.File

/**
 * Created by Leon on 2022/7/7
 */
interface IImageLoader {
    fun load(iv:ImageView, url:String?)
    fun load(iv:ImageView, file: File?)
    fun loadBase64(iv:ImageView, url:String)
}