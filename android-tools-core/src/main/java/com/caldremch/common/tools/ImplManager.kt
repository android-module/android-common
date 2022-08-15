package com.caldremch.common.tools

import org.koin.java.KoinJavaComponent

/**
 * Created by Leon on 2022/7/12
 */
object ImplManager {
     @JvmStatic
     val storage: IStorage by KoinJavaComponent.inject(IStorage::class.java)
     @JvmStatic
     val imageLoader: IImageLoader by KoinJavaComponent.inject(IImageLoader::class.java)
     @JvmStatic
     val toast: IToast by KoinJavaComponent.inject(IToast::class.java)
}