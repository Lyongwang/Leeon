package com.github.buildsrc.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.github.buildsrc.visitor.ClassTraceVisistor
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.internal.impldep.org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassReader.EXPAND_FRAMES
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * Created by Lyongwang on 2020/12/13 18: 12.
 *
 * Email: liyongwang@yiche.com
 */
class TraceTransform: Transform() {
    // transfrom名字会在依赖项目的task other 中生成对应的transformClassesWithTrace任务
    override fun getName(): String {
        return "trace"
    }

    // 处理那些类型
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    // 是否支持增量编译
    override fun isIncremental(): Boolean {
        return false
    }

    // 处理范围
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // 处理干预过程的方法
    override fun transform(transformInvocation: TransformInvocation) {
        // 父类方法为空实现
//        super.transform(transformInvocation)
        // 输入内容为包含所有class文件的文件夹或jar文件
        transformInvocation.inputs.forEach { transformInput ->
            transformInput.directoryInputs.forEach { dirInput ->
                // 操作文件
                traceDirFiles(dirInput, transformInvocation.outputProvider)
            }
            transformInput.jarInputs.forEach {jarInput ->
                // 操作jar文件
                traceJarFiles(jarInput, transformInvocation.outputProvider)

            }
        }
    }

    private fun traceJarFiles(jarInput: JarInput, outputProvider: TransformOutputProvider) {
        // 如果不是jar结尾的 META-INF 中的文件等 不处理
        if (!jarInput.file.absolutePath.endsWith(".jar")){
            println(jarInput.file.absolutePath)
            return
        }
        // 不能直接对jar文件进行操作  创建临时文件后替换原有文件
        val tempFile = File(jarInput.file.parentFile, "${jarInput.file.name}.temp").also { it.createNewFile() }

        // 读取jar文件
        JarFile(jarInput.file).use { jarFile ->
            // 创建临时jar文件输出流
            JarOutputStream(FileOutputStream(tempFile)).use {jarOutputStream ->
                // 读取jar文件中的文件
                jarFile.entries().iterator().forEach {jarEntry ->
                    // 使用zip压缩读取jar文件中的文件类
                    val zipEntry = ZipEntry(jarEntry.name)
                    jarFile.getInputStream(zipEntry).use {inputStream ->
                        // 只处理class文件
                        if (jarEntry.name.endsWith(".class")){
                            jarOutputStream.putNextEntry(zipEntry)
                            val classReader = ClassReader(IOUtils.toByteArray(inputStream))
                            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            classReader.accept(ClassTraceVisistor(classWriter), ClassWriter.COMPUTE_FRAMES)
                            // 将处理好的class文件写入到临时jar文件中
                            jarOutputStream.write(classWriter.toByteArray())
                        } else {
                            // 非class文件直接写入临时jar文件
                            println("${jarEntry.name} <- jarEntry.name")
                            jarOutputStream.putNextEntry(zipEntry)
                            jarOutputStream.write(IOUtils.toByteArray(inputStream))
                        }
                    }
                }
                jarOutputStream.closeEntry()
            }
        }

        // 目标文件目录
        val dest = outputProvider.getContentLocation(
                jarInput.file.nameWithoutExtension + DigestUtils.md5Hex(jarInput.file.absolutePath),
                jarInput.contentTypes,
                jarInput.scopes,
                Format.JAR)

        // 将临时文件拷贝到目标文件
        FileUtils.copyFile(tempFile, dest)

    }

    private fun traceDirFiles(dirInput: DirectoryInput, outputProvider: TransformOutputProvider) {
        // 遍历所有文件
        dirInput.file.walkTopDown()
                .filter { it.isFile }
                .forEach {file ->
                    //  创建文件流 use会自动关闭流的操作符
                    FileInputStream(file).use { fis ->
                        // ClassReader&ClassWriter 是org.ow2.asm:asm:7.1 中的
                        val classReader = ClassReader(fis)
                        val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                        // 类访问者对象
                        val classTraceVisistor = ClassTraceVisistor(classWriter)
                        // 接收数据插入内容
                        classReader.accept(classTraceVisistor, EXPAND_FRAMES)

                        file.writeBytes(classWriter.toByteArray())
                    }
                }

        val dest = outputProvider.getContentLocation(dirInput.name,
                dirInput.contentTypes, dirInput.scopes, Format.DIRECTORY)
        FileUtils.copyDirectory(dirInput.file, dest)
    }

}