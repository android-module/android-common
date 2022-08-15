package com.caldremch.common.tools

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

/**
 * Created by Leon on 2022/7/7
 */
internal class ImageLoaderImpl : IImageLoader {
    override fun load(iv: ImageView, url: String?) {
        Glide.with(iv).load(url).into(iv)
    }

    override fun load(iv: ImageView, file: File?) {
        Glide.with(iv).load(file).into(iv)
    }

    override fun loadBase64(iv: ImageView, url: String) {
        Glide.with(iv.context)
            .load("data:image/jpg;base64,$url")
    }
}