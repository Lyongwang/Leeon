package com.github.processor;

import com.github.annotation.service.ServiceRouter;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;

/**
 * 服务注解处理器
 * @author Lyongwang
 * @date 2020/4/29 16: 11
 * <p>
 * Email: liyongwang@yiche.com
 */
public class SerivceProcessor extends AbstractProcessor {
    private Map<String, String> mOptions;

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

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        LogUtil.log(mMessager, "=====> SerivceProcessor##process");
        return true;
    }
}
