package com.github.loginlib.tools

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.github.common.base.AppInfo
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Lyongwang on 2020/9/10 18: 28.
 *
 * Email: liyongwang@yiche.com
 */

object PreferenceUtil{
    var LOGIN_INPUT_ACOUNT_NAME by PreferenceDelegates.any("")
    var age by PreferenceDelegates.any(0)
}

private object PreferenceDelegates{
    private const val TAG = "PreferenceDelegates"
    private const val SP_NAME = "leeon_preferences"
    /**
     * 创建SharedPreferences 对象
     */
    private val preferences : SharedPreferences by lazy { AppInfo.getApplication().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE) }

    /**
     * 定义委托获取和设置对应类型的方法
     */
    fun <T> any(defaultValue: T) = object: ReadWriteProperty<Any, T>{
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            Log.i(TAG, "call method getValue $thisRef && ${property.name} ")
            val res:Any = when(defaultValue){
                is Int -> preferences.getInt(property.name, defaultValue)
                is Boolean -> preferences.getBoolean(property.name, defaultValue)
                is String -> preferences.getString(property.name, defaultValue)
                is Float -> preferences.getFloat(property.name, defaultValue)
                is Long -> preferences.getLong(property.name, defaultValue)
                else -> throw IllegalArgumentException("getValue method get defaultValue $defaultValue wrong type")
            }
            return res as T
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            val editor:SharedPreferences.Editor = preferences.edit()
            when(value){
                is Int -> editor.putInt(property.name, value)
                is Boolean -> editor.putBoolean(property.name, value)
                is String -> editor.putString(property.name, value)
                is Float -> editor.putFloat(property.name, value)
                is Long -> editor.putLong(property.name, value)
                else -> throw IllegalArgumentException("setValue method get param $value wrong type")
            }
            editor.apply();
        }
    }
}