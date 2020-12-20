package com.github.buildsrc.visitor

import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.MethodVisitor as MethodVisitor1


/**
 * Created by Lyongwang on 2020/12/13 19: 13.
 *
 * Email: liyongwang@yiche.com
 * 方法访问处理器
 */
class MethodTraceVisitor(api: Int, methodVisitor: MethodVisitor1, access: Int, name: String?, descriptor: String?)
    : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    lateinit var className: String

    /**
     * 方法进入之后
     */
    override fun onMethodEnter() {
        super.onMethodEnter()
        // 插入统计耗时方法 如何知道插入内容  1 写原生代码 -> 2 编译生成class文件 -> 3 code ASM ByteCode viewer/clases右键 -> 4 ASMMiFied
        mv.visitLdcInsn("$className${this.name}")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/os/Trace", "beginSection", "(Ljava/lang/String;)V", false)
    }

    /**
     * 方法退出之前
     */
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        // 插入统计耗时结束方法
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/os/Trace", "endSection", "()V", false)
    }
}