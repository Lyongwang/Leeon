package com.github.buildsrc.plugin

import com.android.build.gradle.AppExtension
import com.github.buildsrc.transform.TraceTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Lyongwang on 2020/12/13 18: 08.
 *
 * Email: liyongwang@yiche.com
 */
class TracePlugin: Plugin<Project> {
    // https://shimo.im/sheets/XtDtYdq8qh3yXrpg/dMjzp
    override fun apply(project: Project) {
        // 注册一个transform 打包时对每个方法增加扩展
        // 如果没有注册自定义的transfrom 系统自动实现将生成的class文件存放到指定目录进行下一步(生成dex)的操作
        // 如果注册了自定义transform 需要自己将处理过的class文件存放到指定目录
        project.extensions.getByType(AppExtension::class.java)
                .registerTransform(TraceTransform())
    }
}