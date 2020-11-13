package com.github.processor;

import com.github.annotation.TextUtils;
import com.github.annotation.router.RouterConstains;
import com.github.annotation.router.RouterTable;
import com.github.annotation.service.IBundleService;
import com.github.annotation.service.MethodInfo;
import com.github.annotation.service.ServiceConstances;
import com.github.annotation.service.ServiceMethod;
import com.github.annotation.service.ServiceRouter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.lang.model.util.Types;

/**
 * 服务注解处理器
 *
 * @author Lyongwang
 * @date 2020/4/29 16: 11
 * <p>
 * Email: liyongwang@yiche.com
 */
public class SerivceProcessor extends AbstractProcessor {
    private Map<String, String> mOptions;

    private static final String SERVICE_METHOD_NAME = "regist";
    private static final String METHOD_OBJ          = "Method";
    private static final String PARAM_LIST          = "Params";
    private static final String PARAM_OBJ          = "Param";

    private Messager mMessager;
    private Filer    mFiler;
    private Types    mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
        mOptions = processingEnvironment.getOptions();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ServiceRouter.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * MethodInfo method = new MethodInfo();
     * method.setClassName("com.leeon.personcenter.PersonImpl");
     * method.setMethodName("getUserId");
     * RouterTable.putMethod("leeon://leeon.test/personcenter.personImpl?method=getUserId&key1=value1&key2=value2", method);
     */

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        LogUtil.log(mMessager, "=====> SerivceProcessor##process");


        // 获取所有服务element
        Set<? extends Element> serviceClassElements = roundEnvironment.getElementsAnnotatedWith(ServiceRouter.class);
        if (serviceClassElements == null || serviceClassElements.isEmpty()) {
            return true;
        }
        ClassName methodInfoClassName = ClassName.get(MethodInfo.class);
        ClassName routerTableClassName = ClassName.get(RouterTable.class);
        for (Element classElement : serviceClassElements) {
            Set<String> methodNameCache = new HashSet<>();
            String packageName = classElement.getEnclosingElement().toString();
            String className = classElement.getSimpleName().toString();
            ServiceRouter serviceClass = classElement.getAnnotation(ServiceRouter.class);
            String serviceClassName = serviceClass.value();
            List<? extends Element> methodElements = classElement.getEnclosedElements();
            MethodSpec.Builder registServiceMethodBuilder = MethodSpec.methodBuilder(SERVICE_METHOD_NAME)
                    .addModifiers(Modifier.PUBLIC);
            for (Element methodElement : methodElements) {
                ServiceMethod serviceMethod = methodElement.getAnnotation(ServiceMethod.class);
                if (serviceMethod == null) {
                    continue;
                }

                String methodName = serviceMethod.value();
                if (TextUtils.isEmpty(methodName)) {
                    methodName = methodElement.getSimpleName().toString();
                }
                String methodObject = methodName.concat(METHOD_OBJ);
                if (!methodNameCache.contains(methodObject)) {
                    methodNameCache.add(methodObject);
                    registServiceMethodBuilder
                            .addStatement("$T $L = new $T()", methodInfoClassName, methodObject, methodInfoClassName)
                            .addStatement("$L.setClassName($S)", methodObject, packageName.concat(".").concat(className))
                            .addStatement("$L.setMethodName($S)", methodObject, methodName)
                            .addStatement("$T.putMethod($S, $L)", routerTableClassName,
                                    getMethodScheme(serviceClassName, methodName), methodObject);
                } else {
                    LogUtil.log(mMessager, "repeat method --> " + methodObject);
                }
            }

            TypeSpec typeSpec = TypeSpec.classBuilder(ClassName.get(packageName, getClassName(classElement)))
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(registServiceMethodBuilder.build())
                    .addSuperinterface(ClassName.get(IBundleService.class))
                    .build();
            try {
                JavaFile.builder(ServiceConstances.PACKAGE_NAME, typeSpec).build().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private String getMethodScheme( String serviceClassName, String methodName) {
        LogUtil.log(mMessager, "------> " + serviceClassName);
        return serviceClassName.concat(RouterConstains.METHOD_PROFIX).concat(methodName);
    }

    private String getClassName(Element element) {
        return element.getSimpleName().toString()
                .concat(RouterConstains.CLASS_NAME_SEPARATOR)
                .concat(RouterConstains.SERVICE_SUFIX);
    }

}
