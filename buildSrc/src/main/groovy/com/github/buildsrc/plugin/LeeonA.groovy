package com.github.buildsrc.plugin

import com.android.build.gradle.BaseExtension
import com.github.buildsrc.LeeonExtension
import com.github.buildsrc.transform.LeeonTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Lyongwang on 2020/12/13 13: 45.
 *
 * Email: liyongwang@yiche.com
 */
class LeeonA implements Plugin<Project>{
    @Override
    void apply(Project target) {
        def extension= target.extensions.create("leeon", LeeonExtension)
        target.afterEvaluate({
            print( "hello plugin " + extension.user)
        })

        // 获取Android基础的打包Extension
        def baseExtension = target.extensions.getByType(BaseExtension)
        // 创建自己的transform
        def transform = new LeeonTransform()
        // 将自己创建的transform注册到baseExtension中
        // 此时gradle task 中会出现LeeonTransform.getName对应的task transform**leeon**Debug
//        baseExtension.registerTransform(transform)
    }
}