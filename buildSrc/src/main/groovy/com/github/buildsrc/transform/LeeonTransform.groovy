package com.github.buildsrc.transform

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

/**
 * Created by Lyongwang on 2020/12/13 13: 47.
 *
 * Email: liyongwang@yiche.com
 */
class LeeonTransform extends Transform{

    @Override
    String getName() {
        return "leeon" // gradle task name
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        // 干预那些类型 常量为一个set集合, 可以自定义
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        // 干预那些范围 所有项目 还是主项目 常量为一个set集合, 可以自定义
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        // 是否支持增量编译
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        // 正式的干预处理过程  内部没有实现,如增加插件干预 需单独处理
//        super.transform(transformInvocation)
        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider
        inputs.each {
            // 读取其中的jar文件  拷贝到打包目录
            it.jarInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.JAR)
                println("jar file : ${it.file}" )
                println("dest jar: ${dest}")
                FileUtils.copyFile(it.file, dest)
            }

            // 读取其中的class文件  拷贝到打包目录
            it.directoryInputs.each {
                File dest = outputProvider.getContentLocation(it.name, it.contentTypes, it.scopes, Format.DIRECTORY)
                println("dir file : ${it.file}" )
                println("dest dir: ${dest}")
                FileUtils.copyDirectory(it.file, dest)
            }
        }

    }
}