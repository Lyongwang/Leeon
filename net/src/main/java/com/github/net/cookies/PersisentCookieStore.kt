package com.github.net.cookies

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import okhttp3.Cookie
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.collections.map as map

/**
 * Created by Lyongwang on 2020/10/17 13: 19.
 *
 * Email: liyongwang@yiche.com
 */
class PersisentCookieStore(context: Context) {
    private val cookieCache = HashMap<String, MutableList<Cookie>>()
    private val COOKIE_PREFS: String = "cookie_prefs"
    private val COOKIE_PREFS_KEY: String = "cookie_prefs_key"
    private val prefs: SharedPreferences = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE)

    fun add(cookies: MutableList<Cookie>){
        addToCache(cookies)
        addToDisk(cookieCache)
    }

    /**
     * 读取cookie
     * 先读缓存  缓存中没有再从preference读取
     */
    fun get(host:String): MutableList<Cookie> {
        if (cookieCache.isNotEmpty()){
            return cookieCache[host]?: mutableListOf()
        } else {
            val stringSet = prefs.getStringSet(host, null)
            return if (stringSet == null || stringSet.isEmpty()){
                mutableListOf()
            } else {
                stringSet.map { decodeBase64(it) }
                        .forEach{
                            val host = it.domain
                            if (cookieCache[host] == null){
                                cookieCache[host] = mutableListOf(it)
                            } else{
                                cookieCache[host]?.add(it)
                            }
                        }
                cookieCache[host]?: mutableListOf()
            }
        }
    }

    /**
     * 将读取的字符串反序列化成cookie对象
     * 1，将该字符串使用 Base64 解码为字节数组
     * 2，将字节数据反序列化为 SerializableCookie 对象
     * 3，从 SerializableCookie 对象中获取 Cookie 对象并返回。
     */
    private fun decodeBase64(code: String): Cookie {
        val bytes = Base64.decode(code, Base64.DEFAULT)
        val bais = ByteArrayInputStream(bytes)
        val ois = ObjectInputStream(bais)
        return (ois.readObject() as SerializableCookie).cookie()
    }

    private fun addToDisk(cookies: HashMap<String, MutableList<Cookie>>) {
        val set = HashSet<String>()
        cookies.flatMap { it.value }
                .map { encodeBase64(it) }
                .forEach { set.add(it) }
        prefs.edit().putStringSet(COOKIE_PREFS_KEY, set).apply()
    }

    private fun addToCache(cookies: MutableList<Cookie>) {
        cookies.forEach {
            val host = it.domain
            if (it.expiresAt > System.currentTimeMillis()){
                if (cookieCache[host] == null){
                    cookieCache[host] = mutableListOf(it)
                } else {
                    cookieCache[host]?.add(it)
                }
            } else {
                val cookies: MutableList<Cookie>? = cookieCache[host]
                cookies?.remove(it)
            }
        }
    }

    /**
     * 将一个 Cookie 对象序列化为字符串
     * 1，将 Cookie 对象转换为可序列化的 SerializableCookie 对象
     * 2，将 SerializableCookie 序列化为 ByteArray
     * 3，将 ByteArray 使用 Base64 编码并生成字符串
     * @param cookie 需要序列化的 Cookie 对象
     * @return 序列化之后的字符串
     */
    private fun encodeBase64(cookie: Cookie): String {
        var objectBuffer: ObjectOutputStream? = null
        try {
            val buffer  = ByteArrayOutputStream()
            objectBuffer = ObjectOutputStream(buffer)
            objectBuffer.writeObject(SerializableCookie(cookie))
            val encode = Base64.encode(buffer.toByteArray(), Base64.DEFAULT)
            return String(encode)
        } catch (e: Exception) {
            throw e
        } finally {
            try {
                objectBuffer?.close()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 清理cookie
     */
    fun clear(){
        cookieCache.clear()
        prefs.edit().clear().apply()
    }
}