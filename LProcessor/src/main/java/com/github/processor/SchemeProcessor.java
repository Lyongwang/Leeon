package com.github.processor;

import com.github.annotation.router.IRouterInit;
import com.github.annotation.router.RouterActivity;
import com.github.annotation.router.RouterConstains;
import com.github.annotation.router.RouterTable;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * scheme跳转注解处理器
 * @author Lyongwang
 * @date 2020/4/10 11: 27
 * <p>
 * Email: liyongwang@yiche.com
 */
public class SchemeProcessor extends AbstractProcessor {

    private Messager mMessager;
    private String mModuleName;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mModuleName = processingEnvironment.getOptions().get("moduleName");
        LogUtil.log(mMessager, "=====> SchemeProcessor##init");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(RouterActivity.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        LogUtil.log(mMessager, "=====> SchemeProcessor##process");

        Set<? extends Element> routerElements = roundEnvironment.getElementsAnnotatedWith(RouterActivity.class);
        if (routerElements == null || routerElements.isEmpty()){
            return false;
        }
        String pkgName = RouterConstains.PACKAGE_NAME;
        String className = getClassName();
        LogUtil.log(mMessager, "----> start process");
        MethodSpec.Builder initBuilder = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC);
        ClassName routerTableName = ClassName.get(RouterTable.class);
        for (Element element : routerElements) {
            RouterActivity annotation = element.getAnnotation(RouterActivity.class);
            ClassName targetClass = ClassName.get(element.getEnclosingElement().toString(), element.getSimpleName().toString());
            String scheme = getScheme(annotation);
            if (!"".equals(scheme)) {
                initBuilder.addStatement("$T.put($S, $T.class)", routerTableName, scheme, targetClass);
            }
        }

        TypeSpec routerInitSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(IRouterInit.class)
                .addMethod(initBuilder.build())
                .build();
        try {
            JavaFile.builder(pkgName, routerInitSpec).build()
                    .writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回值表示是否还有其他注解处理器需要处理改注解, false 无 true 有
        return true;
    }

    private String getClassName() {
        String moduleName = Character.toUpperCase(this.mModuleName.charAt(0)) + this.mModuleName.substring(1);
        return moduleName.concat(RouterConstains.CLASS_NAME_SEPARATOR).concat(RouterConstains.ROUTER_NAME_SUFIX);
    }

    private String getScheme(RouterActivity annotation) {
        if (annotation == null) {
            return "";
        } else {
            return annotation.scheme().concat(RouterConstains.SCHEME_SEPARATOR)
                    .concat(annotation.host())
                    .concat(RouterConstains.PATH_SEPARATOR).concat(annotation.path());
        }
    }
}
