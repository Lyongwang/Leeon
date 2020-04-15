package com.github.processor;

import com.github.annotation.router.IParamInjector;
import com.github.annotation.router.IntentParam;
import com.github.annotation.router.RouterConstains;
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
import javax.lang.model.type.TypeKind;

/**
 * @author Lyongwang
 * @date 2020/4/14 15: 34
 * <p>
 * Email: liyongwang@yiche.com
 */
public class ParamsProcessor extends AbstractProcessor {
    private static final String   EXTRA       = "extra";
    private static final String   PARAM_VALUE = "ParamValue";
    private              Filer    mFiler;
    private              Messager mMessager;
    private              String   mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        mModuleName = processingEnvironment.getOptions().get("moduleName");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        LogUtil.log(mMessager, "----> ParamsProcessor#process");

        Set<? extends Element> paramElements = roundEnvironment.getRootElements();

        ClassName stringClassName = ClassName.get("java.lang", "String");
        ClassName boolClassName = ClassName.get("java.lang", "Boolean");
        ClassName exceptionClassName = ClassName.get("java.lang", "Exception");
        ClassName bundleClassName = ClassName.get("android.os", "Bundle");
        ClassName objectClassName = ClassName.get("java.lang", "Object");

        boolean hasExtra = false;
        for (Element element : paramElements) {
            String pkgName = element.getEnclosingElement().toString();
            String className = element.getSimpleName().toString();
            String injectClassName = className.concat(RouterConstains.CLASS_NAME_SEPARATOR)
                    .concat(RouterConstains.PARAM_NAME_SUFIX);

            MethodSpec.Builder injectMethodBuilder = MethodSpec.methodBuilder("inject")
                    .addModifiers(Modifier.PUBLIC);
            boolean hasParamAnnotation = false;
            for (Element elementField : element.getEnclosedElements()) {
                IntentParam paramAnnotation = elementField.getAnnotation(IntentParam.class);
                if (paramAnnotation == null) {
                    continue;
                }
                hasParamAnnotation = true;
                String intentKey = paramAnnotation.value();
                if ("".equals(intentKey)) {
                    intentKey = elementField.getSimpleName().toString();
                }
                String fieldName = elementField.getSimpleName().toString();
                if (!hasExtra) {
                    hasExtra = true;
                    injectMethodBuilder.addStatement("$T $L = this.target.getIntent().getExtras()", bundleClassName, EXTRA)
                            .beginControlFlow("if($L == null)", EXTRA)
                            .addStatement("return")
                            .endControlFlow();
                }

                String realParamValue = intentKey.concat(PARAM_VALUE);
                injectMethodBuilder.addStatement("$T $L = $L.get($S)", objectClassName, realParamValue, EXTRA, intentKey)
                        .beginControlFlow("try");

                injectMethodBuilder
                        .beginControlFlow("if($L instanceof $T)", realParamValue, stringClassName)
                        .addStatement("this.target.$L = $T.$L(($T)$L)", fieldName, getClassName(elementField), getOperation(elementField), stringClassName, realParamValue)
                        .nextControlFlow("else")
                        .addStatement("this.target.$L = ($L) $L", fieldName, getTypeName(elementField), realParamValue)
                        .endControlFlow()
                        .nextControlFlow("catch ($T e)", exceptionClassName)
                        .addStatement("e.printStackTrace()")
                        .endControlFlow();
            }

            if (!hasParamAnnotation) {
                continue;
            }

            ClassName parentClassName = ClassName.get(pkgName, className);
            MethodSpec constructorMethod = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PRIVATE)
                    .addParameter(parentClassName, "target")
                    .addStatement("this.target = target")
                    .build();
            MethodSpec injectMethod = injectMethodBuilder.build();
            TypeSpec paramInjectSpec = TypeSpec.classBuilder(ClassName.get(pkgName, injectClassName))
                    .addField(parentClassName, "target", Modifier.PRIVATE)
                    .addMethod(constructorMethod)
                    .addMethod(injectMethod)
                    .addSuperinterface(ClassName.get(IParamInjector.class))
                    .build();

            try {
                JavaFile.builder(pkgName, paramInjectSpec).build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private String getTypeName(Element elementField) {
        TypeKind kind = elementField.asType().getKind();
        if (kind == TypeKind.DOUBLE){
            return "Double";
        } else if (kind == TypeKind.FLOAT){
            return "Float";
        } else if (kind == TypeKind.LONG){
            return "Long";
        } else if (kind == TypeKind.INT){
            return "int";
        } else if (kind == TypeKind.BOOLEAN){
            return "boolean";
        } else {
            return "String";
        }
    }

    private String getOperation(Element elementField) {
        TypeKind kind = elementField.asType().getKind();
        if (kind == TypeKind.DOUBLE){
            return "parseDouble";
        } else if (kind == TypeKind.FLOAT){
            return "parseFloat";
        } else if (kind == TypeKind.LONG){
            return "parseLong";
        } else if (kind == TypeKind.INT){
            return "parseInt";
        } else if (kind == TypeKind.BOOLEAN){
            return "parseBoolean";
        } else {
            return "valueOf";
        }
    }

    private ClassName getClassName(Element elementField) {
        TypeKind kind = elementField.asType().getKind();
        String pkgName = "java.lang";
        if (kind == TypeKind.DOUBLE){
            return ClassName.get(pkgName, "Double");
        } else if (kind == TypeKind.FLOAT){
            return ClassName.get(pkgName, "Float");
        } else if (kind == TypeKind.LONG){
            return ClassName.get(pkgName, "Long");
        } else if (kind == TypeKind.INT){
            return ClassName.get(pkgName, "Integer");
        } else if (kind == TypeKind.BOOLEAN){
            return ClassName.get(pkgName, "Boolean");
        } else {
            return ClassName.get(pkgName, "String");
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(IntentParam.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
