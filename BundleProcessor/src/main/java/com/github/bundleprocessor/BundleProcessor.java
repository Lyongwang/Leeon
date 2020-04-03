package com.github.bundleprocessor;

import com.github.bundleannotation.BundleCode;
import com.github.bundleannotation.BundleData;
import com.github.bundleannotation.BundleInfo;
import com.github.bundleannotation.BundlePropery;
import com.github.bundleannotation.BundleStorage;
import com.github.bundleannotation.Constains;
import com.github.bundleannotation.IBundleInit;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by Lyongwang on 2020/3/31 17: 13.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleProcessor extends AbstractProcessor {
    private static final String SUFFIX = "$Bundle";
    private Filer filer;
    private Messager mMessager;
    private Elements emementUtils;
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        // 文件操作辅助工具
        filer = processingEnvironment.getFiler();
        // 日志操作复制工具
        mMessager = processingEnvironment.getMessager();
        // 元素操作辅助工机
        emementUtils = processingEnvironment.getElementUtils();
        moduleName = processingEnvironment.getOptions().get("moduleName");
        log("---> init method be called : " + moduleName);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        log("---> process method be called");
        /*
           1. set：携带getSupportedAnnotationTypes()中的注解类型，一般不需要用到。
           2. roundEnvironment：processor将扫描到的信息存储到roundEnvironment中，从这里取出所有使用BundleInfo注解的字段。
          */
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BundleInfo.class);
        if (elements.isEmpty()){
            return false;
        } else {
            log("---------------------------- start");
        }

        String className = getClassName();

//        Iterator<? extends Element> iterator = elements.iterator();
        // 获取注解类所在的包
//        PackageElement packageElement = emementUtils.getPackageOf(iterator.next());
//        String pkgName = packageElement.getQualifiedName().toString();
        String pkgName = Constains.PACKAGE_NAME;

        MethodSpec.Builder initMethodBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC);
        for (Element element : elements){
            log("process element [" + element.getSimpleName().toString() + "]");
            BundleInfo info = element.getAnnotation(BundleInfo.class);
            ClassName bundleData = ClassName.get(BundleData.class.getPackage().getName(), BundleData.class.getSimpleName());
            ClassName storage = ClassName.get(BundleStorage.class.getPackage().getName(), BundleStorage.class.getSimpleName());
            ClassName bundleProperty = ClassName.get(BundlePropery.class.getPackage().getName(), BundlePropery.class.getSimpleName());
            ClassName bundleCode = ClassName.get(BundleCode.class.getPackage().getName(), BundleCode.class.getSimpleName());
            String referenceName = element.getEnclosingElement().toString().concat(".").concat(element.getSimpleName().toString());
            initMethodBuilder
                    .addStatement("BundleData data = new $T($T.$L, $T.$L, $S, $S, $S, $S)"
                            , bundleData, bundleProperty, info.p(), bundleCode, info.c()
                            , info.n(), info.d(), info.v(), referenceName)
                    .addStatement("$T.put($S, data)", storage, pkgName.concat(".").concat(className));
        }

        TypeSpec classType = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(IBundleInit.class)
                .addMethod(initMethodBuilder.build())
                .build();


        try {
            JavaFile.builder(pkgName, classType)
                    .build().writeTo(filer);
            log("---------------------------- end");
        } catch (IOException e) {
            e.printStackTrace();
            log("---------------------------- throw");
        }

        return false;
    }

    private String getClassName() {
        String moduleName = Character.toUpperCase(this.moduleName.charAt(0)) + this.moduleName.substring(1);
        return moduleName.concat(Constains.CLASS_NAME_SEPARATOR).concat(Constains.CLASS_NAME_SUFIX);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BundleInfo.class.getCanonicalName());
    }

    private void log(String msg) {
//        System.out.println(msg);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "BundleProcessor log " + msg);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
