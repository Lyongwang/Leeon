package com.github.buildsrc.visitor

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * Created by Lyongwang on 2020/12/13 19: 06.
 *
 * Email: liyongwang@yiche.com
 * ClassVisitor 是org.ow2.asm:asm:7.1 中的
 */
class ClassTraceVisistor(classVisitor: ClassVisitor?) : ClassVisitor(Opcodes.ASM7, classVisitor) {

    lateinit var className: String

    // 访问class文件时被调用
    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        // 获取class文件名称
        this.className = name
    }


    // 访问方法时被调用
    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        // 不进行插装的类
        if (className.contains("DigestUtils")) {
            return super.visitMethod(access, name, descriptor, signature, exceptions)
        } else {
            println("${name} <- visitMethod $className")
            return MethodTraceVisitor(
                    Opcodes.ASM7,
                    super.visitMethod(access, name, descriptor, signature, exceptions),
                    access,
                    name,
                    descriptor
            ).also {
                it.className = this.className
            }
        }
    }
}