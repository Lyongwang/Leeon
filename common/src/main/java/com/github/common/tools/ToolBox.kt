package com.github.common.tools

import android.widget.Toast
import com.github.common.base.AppInfo

/**
 * Created by Lyongwang on 2020/10/11 11: 45.
 *
 * Email: liyongwang@yiche.com
 */
fun Any.toast(duration: Int = Toast.LENGTH_SHORT): Toast{
    return Toast.makeText(AppInfo.getApplication(),this.toString(), duration).apply { show() }
}