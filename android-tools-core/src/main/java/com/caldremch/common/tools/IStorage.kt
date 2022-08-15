package com.caldremch.common.tools

import java.lang.reflect.Type

/**
 * Created by Leon on 2022/7/7
 */
interface IStorage {
    fun put(key: String, value: String?)
    fun put(key: String, value: Float)
//    fun put(key: String, value: Double)
    fun put(key: String, value: Long)
    fun put(key: String, value: Int)
    fun put(key: String, value: Boolean)
    fun put(key: String, value: Any?)
    fun get(key: String, defaultValue: String? = null): String?
    fun get(key: String, defaultValue: Float = 0f): Float
//    fun get(key: String, defaultValue: Double = 0.0): Double
    fun get(key: String, defaultValue: Long = 0L): Long
    fun get(key: String, defaultValue: Int = 0): Int
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    fun <T> get(key: String, classOfT: Class<T>): T?
    fun <T> get(key: String, typeOfT: Type): T?
    fun remove(key: String)
    fun clear()
}