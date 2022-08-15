package com.caldremch.common.tools.impl

import com.caldremch.common.tools.IImageLoader
import com.caldremch.common.tools.IStorage
import com.caldremch.common.tools.IToast
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Leon on 2022/7/7
 */


val koinAbsImplModules = module {
    //common
    singleOf<IStorage> { StorageImpl() }
    singleOf<IImageLoader> { ImageLoaderImpl() }
    singleOf<IToast> { ToastImpl() }
}