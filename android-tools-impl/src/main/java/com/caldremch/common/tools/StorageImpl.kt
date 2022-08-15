package com.caldremch.common.tools

import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import java.lang.reflect.Type

/**
 * Created by Leon on 2022/7/7
 */
internal class StorageImpl : IStorage {

    private val sp by lazy { SPUtils.getInstance() }

    override fun put(key: String, value: String?) {
        sp.put(key, value)
    }

    override fun put(key: String, value: Float) {
        sp.put(key, value)
    }


    override fun put(key: String, value: Long) {
        sp.put(key, value)
    }

    override fun put(key: String, value: Int) {
        sp.put(key, value)
    }

    override fun put(key: String, value: Boolean) {
        sp.put(key, value)
    }

    override fun put(key: String, value: Any?) {
        put(key, GsonUtils.toJson(value))
    }

    override fun get(key: String, defaultValue: String?): String? {
        return sp.getString(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Float): Float {
        return sp.getFloat(key, defaultValue)

    }


    override fun get(key: String, defaultValue: Long): Long {
        return sp.getLong(key, defaultValue)
    }

    override fun get(key: String, defaultValue: Int): Int {
        return sp.getInt(key, defaultValue)
    }

    override fun <T> get(key: String, classOfT: Class<T>): T? {
        val value = sp.getString(key)
        if (value.isNullOrEmpty()) return null
        return GsonUtils.fromJson(value, classOfT)
    }

    override fun <T> get(key: String, typeOfT: Type): T? {
        val value = sp.getString(key)
        if (value.isNullOrEmpty()) return null
        return GsonUtils.fromJson(value, typeOfT)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key)
    }

    override fun remove(key: String) {
       sp.remove(key)
    }

    override fun clear() {
       sp.clear()
    }
}