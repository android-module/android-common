package com.caldremch.common.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Leon on 2022/8/18.
 */
object TypeUtils {

    fun <T> getClz(obj: Any, index: Int): Class<T> {
        obj.javaClass.genericSuperclass?.let { type ->
            type as ParameterizedType
            val types: Array<Type> = type.actualTypeArguments
            return types[index] as Class<T>
        }
        throw RuntimeException("error get type")
    }

}