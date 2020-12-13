package com.github.buildsrc.visitor

import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.MethodVisitor as MethodVisitor1


/**
 * Created by Lyongwang on 2020/12/13 19: 13.
 *
 * Email: liyongwang@yiche.com
 */
class MethodTraceVisitor(api: Int, methodVisitor: MethodVisitor1, access: Int, name: String?, descriptor: String?)
    : AdviceAdapter(api, methodVisitor, access, name, descriptor) {
    lateinit var className: String

    /**
     * 方法进入之后
     */
    override fun onMethodEnter() {
        super.onMethodEnter()
        mv.visitLdcInsn("$className${this.name}")
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/os/Trace", "beginSection", "(Ljava/lang/String;)V", false)
    }

    /**
     * 方法退出之前
     */
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/os/Trace", "endSection", "()V", false)
    }
}