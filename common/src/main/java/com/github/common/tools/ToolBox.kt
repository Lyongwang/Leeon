package com.github.common.tools

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.widget.Toast
import com.github.common.base.AppInfo
import com.google.gson.Gson
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec

/**
 * Created by Lyongwang on 2020/10/11 11: 45.
 *
 * Email: liyongwang@yiche.com
 */
fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast{
    return Toast.makeText(AppInfo.getApplication(),this.toString(), duration).apply { show() }
}

private val des_key = byteArrayOf(109, 114, 88, 110, 53, 112, 72, 88)
private val des_iv = byteArrayOf(107, 57, 53, 56, 57, 73, 97, 117)

/**
 * des 加密
 */
fun String.encryptDES(): String{
    val sr: SecureRandom = SecureRandom()
    val ks: DESKeySpec = DESKeySpec(des_key)
    val skf = SecretKeyFactory.getInstance("DES")
    val sk = skf.generateSecret(ks)
    val cip = Cipher.getInstance("DES/CBC/PKCS5Padding")
    val parameterSpec = IvParameterSpec(des_iv)
    cip.init(Cipher.ENCRYPT_MODE, sk, parameterSpec, sr)
    val dest = Base64.encode(cip.doFinal(this.toByteArray(Charsets.UTF_8)))
    return String(dest)
}

/**
 * des 解密
 */
fun String.decryptDES(): String{
    val random = SecureRandom()
    val deskey = DESKeySpec(des_key)
    val keyFactory = SecretKeyFactory.getInstance("DES")
    val secureKey = keyFactory.generateSecret(deskey)
    val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
    val parameterSpec = IvParameterSpec(des_iv)
    cipher.init(Cipher.DECRYPT_MODE, secureKey, parameterSpec, random)
    val byte = cipher.doFinal(Base64.decode(this.toCharArray()))
    return String(byte, Charsets.UTF_8)
}

/**
 * 对象转json
 */
fun Any.toJson(): String{
    return Gson().toJson(this)
}

/**
 * json转对象
 */
fun <T> String.fromJson(t: Class<T>): T{
    return Gson().fromJson(this, t)
}