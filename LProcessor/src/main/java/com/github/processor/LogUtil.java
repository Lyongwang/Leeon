package com.github.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Created by Lyongwang on 2020/4/10 11: 30.
 * <p>
 * Email: liyongwang@yiche.com
 */
class LogUtil {
    static void log(Messager messager, String msg) {
        //        System.out.println(msg);
        messager.printMessage(Diagnostic.Kind.NOTE, "BundleProcessor log " + msg);
    }
}
